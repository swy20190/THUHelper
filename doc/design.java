/* ----------- Mission ----------- */
public class BaseMission {
    private final String mMissionID;
    private final String mReleaseUserID;
    private final Date mCreateTime;

    enum MissionState {
        RELEASED, // not accepted yet
        ACCEPTED,
        TIMEOUT, // deadline missed, but not termiated
        FINISHED, // finished, but not evaluated
        INVALID, // invalid due to a stop request, not evaluated
        TERMINATED
    }
    private MissionState mState;

    private String mAcceptUserID = null;
    private String mTitle;
    private int mBonus;
    private Date mDeadline;
}

public class MissionSummary extends BaseMission {}

public class MissionDetail extends BaseMission{
    private ReleaseUser mReleaseUser;
    private AcceptUser mAcceptUser = null;
    private String mDescription;
    private String mFeedback = null;
    private int mScore = -1;
    private LinkedList<Update> mUpdateQueue;
}

public class Update {
    enum UpdateType {
        RELEASE_USER_MESSAGE,
        ACCEPT_USER_MESSAGE,
        SYSTEM_MESSAGE
    }
    private final String mUpdateID;
    private final UpdateType mType;
    private final String mMessage;
    private boolean mReleaseUserNotified = false;
    private boolean mAcceptUserNotified = false;
    private boolean mServerSynchronized = false; // whether the update is sent to server successfully
    private final Date mTime; // time the update occured
}

/* ----------- User ----------- */

public class BaseUser {
    private final String mID;
    private final String mUserName;
    private final String mAvatarURI;   
}

public class User extends BaseUser {
    private final String mAccount;
    // password?
    private String mDormitory;
    private String mMajor;
    private int mReleaseCount;
    private int mAcceptCount;
    private float mScore;
    private LinkedList<String> mReleaseMissionQueue; // ID of released missions
    private LinkedList<String> mAcceptMissionQueue; // ID of accepted missions
}

public class ReleaseUser extends BaseUser {
    private final int mReleaseCount;
}

public class AcceptUser extends BaseUser {
    private final int mAcceptCount;
    private final float mScore;
}

/* ----------- Chat ----------- */
public class Chat {
    private final MissionSummary mMission;
    private final String mReleaseUserID;
    private final String mAcceptUserID; // Initiate a chat only after a mission is accepted
    private ReleaseUser mReleaseUser;
    private AcceptUser mAcceptUser;
    private LinkedList<Update> mUpdateQueue; // message queue
}


/* UI Elements */
public class UserInfoActivity extends AppCompatActivity {
    private User mUser;
}

public class ReleaseTab extends Fragment {
    private LinkedList<MissionSummary> mMissionList;
    private Date mLastRefreshTime;
}

public class ChatTab extends Fragment {
    private LinkedList<Chat> mChatList;
    private Date mLastRefreshTime;
}

public class PersonalCenterTab extends Fragment {
    private User mUser;
}

public class MissionDetailActivity extends AppCompatActivity {
    private MissionDetail mMission;
}

public class ChatActivity extends AppCompatActivity {
    private Chat mChat;
}