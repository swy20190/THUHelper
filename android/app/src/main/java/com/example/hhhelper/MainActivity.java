package com.example.hhhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.function.BiFunction;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerAdapter mPageAdapter;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private CircleImageView avatar;
    private SearchView mSearchView;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();
        //检查登录状态
        checkLogin();
        //检查完毕
        //获得userID
        userID = getUserID();
        //获得完毕
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View drawHeader = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView headerUid = (TextView) drawHeader.findViewById(R.id.nav_head_uid);
        headerUid.setText(userID);
        //select avatar
        avatar = (CircleImageView)drawHeader.findViewById(R.id.icon_image);
        //avatar.setImageResource(R.drawable.ic_photo);
        String base64Avatar = preferences.getString("avatarBase64","");
        if(base64Avatar.equals("")){
            avatar.setImageResource(R.drawable.ic_photo);
        }
        else{
            Bitmap bitmap = null;
            try{
                byte[] bitmapByte  =Base64.decode(base64Avatar, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapByte,0,bitmapByte.length);
                avatar.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"avatar clicked!",Toast.LENGTH_SHORT).show();
                setupDialog();
            }
        });
        //init navigation menu
        initNavMenu(navigationView);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_settings:
                        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        editor = preferences.edit();
                        editor.putString("account","");
                        editor.putString("password","");
                        editor.putBoolean("isEnsured",false);
                        editor.putBoolean("isRemembered",false);
                        editor.commit();
                        Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        FloatingActionButton addTicket = (FloatingActionButton)findViewById(R.id.fab);
        addTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Release.class);
                startActivity(intent);
            }
        });
        titleList.clear();
        titleList.add("我的下单");
        titleList.add("订单市场");
        titleList.add("我的接单");

        fragmentList.clear();
        fragmentList.add(new Fragment_send());
        fragmentList.add(new Fragment_market());
        fragmentList.add(new Fragment_receive());

        mPageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
            @Nullable
            @Override
            public CharSequence getPageTitle(int position){
                return titleList.get(position);
            }
            @Override
            public long getItemId(int position){
                return position;
            }

            @Override
            public int getCount() {
                return titleList.size();
            }
        };
        viewPager.setAdapter(mPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
        checkLogin();
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        //Toast.makeText(this,"onRestart",Toast.LENGTH_SHORT).show();
        checkLogin();
        initNavMenu(navigationView);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        initSearchView();
        return true;
    }
    public void initSearchView(){
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if(TextUtils.isEmpty(newText)){
//                    return false;
//                }
//                showSearchResult(newText);
                return true;
            }
        });
    }

    public void startSearchActivity(String query){
        Intent intent = new Intent(MainActivity.this, SearchResult.class);
        intent.putExtra("queryString",query);
        startActivity(intent);
    }

//    public void showSearchResult(String searchText){
//        // 根据搜索内容显示相应的订单
//        Toast.makeText(this, searchText, Toast.LENGTH_SHORT).show();
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    //choose avatar
    private void setupDialog(){
        final String[] items={"拍照","相册"};
        AlertDialog.Builder listDialog = new AlertDialog.Builder(this);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //TODO
                    File outputImage = new File(getExternalCacheDir(),
                            "output_image.jpg");
                    try{
                        if(outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if(Build.VERSION.SDK_INT>=24){
                        imageUri = FileProvider.getUriForFile(MainActivity.this,
                                "com.example.hhhelper.fileprovider",outputImage);
                    }else{
                        imageUri = Uri.fromFile(outputImage);
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_PHOTO);
                }else if(which == 1){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this, new
                                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }else{
                        openAlbum();
                    }
                }
            }
        });
        listDialog.show();
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"You denied the permission.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri)
                        );
                        avatar.setImageBitmap(bitmap);
                        uploadBitmap2Base64(bitmap);
                        storeBitmapAsBase64(bitmap);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    if (Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            //document 类型 uri,使用 document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap = getBitmapFromUri(this, getImageContentUri(this, imagePath));
            avatar.setImageBitmap(bitmap);
            if(uploadBitmap2Base64(bitmap)){
                //success
                storeBitmapAsBase64(bitmap);
                Toast.makeText(this,"set avatar",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "failed to get avatar",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "failed to get avatar",Toast.LENGTH_SHORT).show();
        }
    }
    public static Uri getImageContentUri(Context context, String path){
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},MediaStore.Images.Media.DATA+"=?",
                new String[]{path},null);
        if(cursor!=null && cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri,""+id);
        }else{
            if(new File(path).exists()){
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }else{
                return null;
            }
        }
    }
    public static Bitmap getBitmapFromUri(Context context, Uri uri){
        try{
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri,"r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void checkLogin(){
        //检查登录状态
        String account = preferences.getString("account", "");
        String password = preferences.getString("password","");
        boolean isEnsured = preferences.getBoolean("isEnsured", false);
        if(isEnsured){
            // mock that the account is valid
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("登录错误");
            builder.setMessage("请登录/注册");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private String getUserID(){
        String ret = preferences.getString("userID","");
        return ret;
    }

    //将头像以base64上传到服务器
    private boolean uploadBitmap2Base64(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
        byte[] bytes = bos.toByteArray();
        String base64Bitmap = Base64.encodeToString(bytes, Base64.DEFAULT);
        //TODO update avatar, it should be a User clas method
        //将base64Bitmap上传到服务器
        return true;
    }

    //将头像以base64存储到本地preference
    private void storeBitmapAsBase64(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,bos);
        byte[] bytes = bos.toByteArray();
        String base64Bitmap = Base64.encodeToString(bytes, Base64.DEFAULT);
        editor = preferences.edit();
        editor.putString("avatarBase64",base64Bitmap);
        Log.d("base64",base64Bitmap);
        editor.commit();
    }

    private void initNavMenu(NavigationView navigationView){
        String nickName = preferences.getString("nickName","");
        String mail = preferences.getString("mail","");
        String dorm = preferences.getString("dorm","");
        if(!nickName.equals("")){
            navigationView.getMenu().getItem(0).setTitle(nickName);
        }
        if(!mail.equals("")){
            navigationView.getMenu().getItem(1).setTitle(mail);
        }
        if(!dorm.equals("")){
            navigationView.getMenu().getItem(2).setTitle(dorm);
        }
    }
}
