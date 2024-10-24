
//内存泄漏检测
LeakCanary

//图片缓存 
Glide
Picasso

//图片选择
pictureselector

//压缩
compress
luban

//网络
okHttp
retrofit

//数据库
Room
sqlite
sqlcipher
Realm


//权限
easypermissions
permissionX

//刷新头
SmartRefresh

//条码识别
Zxing

//数据解析
gson
fastJson

//app h5互相调用
jsBridge

//管理Activity
ActivityStackManager

//动画
Lottie

//多分辨率适配
AndroidAutoSize
插件生成不同的dimens

px
pixels(像素)，屏幕上的实际像素点，无论控件或文字最终都会转化为px单位来显示其大小。

dp
与dip雷同，指的是设备独立像素，在不同分辨率和尺寸的手机上代表了不同的真实像素，计算公式：px = dp*(dpi/160)

dpi
像素密度，指的是在系统软件上指定的单位尺寸的像素数量，它往往是写在系统出厂配置文件的一个固定值。

sp
全称scaled pixels，放大像素的缩写，专门用于处理字体的大小。它不仅与屏幕dpi有关，还与系统的默认字体大小有关。

public float density;  // 屏幕密度, density = dpi/160, dp与px之间的转化就是用此参数

px= dp *（dpi/160）, 
density = dpi/160，

=> px = dp*density 
=> dp = px/density；
=> 屏幕的总 px 宽度 / density = 屏幕的总 dp 宽度
=> 当前设备屏幕总宽度（单位为像素）/ 设计图总宽度（单位为 dp) = density
如果我们将一套设计图的总宽度（dp）作为最终手机屏幕的总宽度（dp）， 
从而达到修改density的目的，同时又可以保证最终不同分辨率手机的屏幕总宽度是相同的，这也就完成了适配。

px 转 dp 的公式 dp = px / density
明白这个后，我再来说说 density，density 在每个设备上都是固定的，DPI / 160 = density，屏幕的总 px 宽度 / density = 屏幕的总 dp 宽度
设备 1，屏幕宽度为 1080px，480DPI，屏幕总 dp 宽度为 1080 / (480 / 160) = 360dp
设备 2，屏幕宽度为 1440，560DPI，屏幕总 dp 宽度为 1440 / (560 / 160) = 411dp