1.测试AlertDialog
	一般AlertDialog
	单选列表AlertDialog
	自定义AlertDialog

2.测试ProgressDialog
	圆形进度ProgressDialog
	水平进度ProgressDialog

3.测试DatePickDialog
         TimePickDialog


AlertDialog:
show():显示警告框，没有公共的构造方法，只能通过内部类Builder来创建
AlertDialog.Builder：
create():创建对象
show():创建对象同时将其显示出来
setTitle(CharSequence title):设置标题
setMessage()
setView(View view):设置Dialog中的视图
