# 登录机制说明书
## 登录信息存储
使用app公有的sharedpreference存储登录信息，包括：
- account(String): 账号
- password(String): 密码
- isEnsured(boolean): 是否经过登录验证（具体用途见下文）
- isRemembered(boolean): 是否记住密码
## 登录状态转化
涉及到登录状态的主要有三个activity:
- MainActivity: 进入main时，首先检查isEnsured，若为否，跳转到LoginActivity
- LoginActivity: 检查isRemembered，若为真，填入账号密码。点击登录按钮，将账号密码提交服务器检查，若通过检查，则将account和password
存储起来，并把isEnsured置为真。若勾选了记住密码，则将isRemembered置为真。然后跳转到main。
- SettingsActivity: 重置密码时，首先对比输入的旧密码与本地存储的旧密码，若符合，则继续。若勾选记住密码，isRemembered置为真。不论如何，在点击
保存密码按钮时，isEnabled置为假，存储新的密码（账户不需要更新）。然后跳转到登录页面。此时，即使用户点击系统的返回键返回main,
main启动调用onCreate()时检查isEnables为假，同样会提示用户跳转到登录页面。