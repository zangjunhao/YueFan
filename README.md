# YueFan

RedRock最终考核

---
[我是APK](https://github.com/zangjunhao/YueFan/blob/master/app/release/app-release.apk)
## 约饭

> 你是否已经吃食堂吃到打干越，去外面一个人吃又太贵，想找室友一起，室友又懒得烧蛇吃
>
> 你是否已经厌倦总是一个人吃饭，每次吃饭都变得那么平淡无味
>
> 约饭 -> 帮你找到身边一起吃饭的人

## 以下用户使用可能会出现问题
- [ ]  中国移动的用户 (因为服务器被中国移动墙了。。)
- [ ]  mips,x86,armeabi,arm-v8等用户（因为此应用只兼容了armeabi-v7a）

##  ==还存在的bug==
- 因为一些地方对服务器的数据没处理好，依旧存在闪退的情况
- 对话列表的刷新有时会出现不按顺序排列的情况

## 实现的功能
- [x] 满足约饭的基本需求
- 发布和查看约饭消息
- 查看你跟发布人的距离
- 查看地图可准确知道发布人位置和吃饭地点
- 查看发布人资料，并可进行即时通讯

## 使用的技术
- 大部分使用mvp进行解耦，少部分使用mvc（强行mvc）
- 使用EventBus对定位和聊天进行及时更新
- 使用Glide加载图片，高效稳定
- 使用LeanCloud作为强大的后端支撑（手动滑稽
- 使用高德地图API进行精确的定位和地图查看

## 应用截图
<img width="300" height="560" src="https://github.com/zangjunhao/YueFan/blob/master/Screenshot_20180827-203010.jpg"/>   <img width="300" height="560" src="https://github.com/zangjunhao/YueFan/blob/master/Screenshot_20180827-211315.jpg"/> 

<img width="300" height="560" src="https://github.com/zangjunhao/YueFan/blob/master/Screenshot_20180827-203045.jpg"/>   <img width="300" height="560" src="https://github.com/zangjunhao/YueFan/blob/master/Screenshot_20180827-211244.jpg"/>  

<img width="300" height="560" src="https://github.com/zangjunhao/YueFan/blob/master/Screenshot_20180827-211332.jpg"/>   <img width="300" height="560" src="https://github.com/zangjunhao/YueFan/blob/master/Screenshot_20180827-203029.jpg"/>  


## 鸣谢
[知乎matisse](https://github.com/zhihu/Matisse)
