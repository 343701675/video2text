# video2text
将视频转换成字符画在控制台播放

一.原理

1.1. 利用ffmpeg.exe将视频转化为一张张图片（关于这个软件，还有其他玩法）

1.2. 使用自写的图片转字符画代码，将图片转字符画

1.3 我们知道视频是由一张张图片播放出来的，那么txt也是可以一个个播放形成视频的

二.操作

我将1，2，3的步骤都封装成了一个jar包，可以直接运行，步骤如下:

1.下面是分别输入视频的地址，帧宽度和帧高度（默认960x540），帧（默认25），等待完成后
2.完成后，在此打开，输入reload，就开始播放字符画视频了



