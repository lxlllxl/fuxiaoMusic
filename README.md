# fuxiaoMusic
拂晓音乐说明文档
一、主界面firstActivity.java---activity_first.xml
放入两个button，一个进入登录主界面，一个进入列表界面

二、点击登录/注册按钮进入Login_register.java--Activity_login_register.xml界面，
里面放入元素：imageview，两个button，其中两个按钮样式调用了drawable里的shape.xml

点击注册按钮进入Register.java---Activity_register.xml界面，里面放入的元素有：3个EditText，3个textciew，即注册按钮，点击注册按钮进入登录界面；

这里的java代码有：验证用户名是否存在，验证两次密码是否输入一致，，其中运用到数据库的内容Sqlite建立数据库名为：message.db,表名为info，字段有id，user，password。
1.验证两次密码是否一致：

2、验证用户名是否存在：

三、点击注册按钮，注册成功跳转到登录界面：LoginActivity.java----Activity_login.xml,
此代码段验证用户名和密码是否与注册的一致，一致就跳转到音乐列表

四、获取音乐信息列表MainActivity.Java----Activity_main.xml

里面的元素有ToolBar,Listview,Nagvation
其中toolbar的内容是，由toolbar.meun组成
点击菜单按钮，出现nagvation界面

这个界面的元素有：nav_head.xml，其中放了cicleImageview和TeextView
在mainActivity中nagvation中app:headlayout=”@layout.nav_head”
c这些菜单项是nav_meun.xml组成；
ListView是显示本地音乐信息，点击Item项跳转到PlayActivity.java---Activity_play.xml

D点击ToolBar上的搜索图标就跳转到网易云音乐界面：wyyActivity.java---Activity_wwy.xml

其中的元素只有webView跳转网页
六：该demo里还有几个类：
Music.java：接受本地音乐的基本信息：歌名、歌词
TimeUtil.java:进行时间的转换
MusicService.java:后台音乐播放服务，
