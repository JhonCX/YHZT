# yhzt

#### 介绍
**YHZT**</br>
&emsp;&emsp;这是一款针对AION EMU genermany（国内为风大AL AION）编写的免费开源的中文购买商城。

#### 使用技术
JDK1.8
spring boot  
EasyWeb UI

#### 安装教程

1.使用navicat导入shop_list.sql 和 shop_list_item 表到gs数据库下

2.代码拉取到本地后，打成Jar包后，新建记事本，命名为: XXX.bat 并添加以下内容
```
@echo off
echo.
SET PATH="JRE1.8\bin"
title 新梦商城
color 2E
echo. 
java -jar yhzt-1.0.jar  --server.port=233 
```
其中 `--server.port=233`中 ###233### 可以修改为自定义的端口

#### 使用说明

1. 进入管理入口 需要GM等级大于等于5
2. 商城购买物品通过调查问卷领取 延时在一分钟
3. 商城默认领取间隔为20分钟 
	修改 ###AL-Game\config\main###下的 ###security.properties### 文件 
	修改
	gameserver.security.survey.delay.minute = 20
	为
	gameserver.security.survey.delay.minute = 1
	这样购买的物品领取间隔为1分钟

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)