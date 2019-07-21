# yhzt

## 介绍
**YHZT**<br/>
&emsp;&emsp;这是一款针对AION EMU genermany（国内为风大AL AION）编写的免费开源的中文购买商城。

## 使用
&emsp;&emsp;JDK1.8 <br/>
&emsp;&emsp;spring boot  <br/>
&emsp;&emsp;EasyWeb UI

## 安装教程

&emsp;&emsp;1.使用navicat导入shop_list.sql 和 shop_list_item 表到gs数据库下

&emsp;&emsp;2.代码拉取到本地后，打成Jar包后，新建记事本，命名为: XXX.bat 并添加以下内容
```
@echo off
echo.
SET PATH="JRE1.8\bin"
title 新梦商城
color 2E
echo. 
java -jar yhzt-1.0.jar  --server.port=233 
```
&emsp;&emsp;其中 `--server.port=233`中  **233** 可以修改为自定义的端口

## 使用说明

&emsp;&emsp;1. 进入管理入口 需要GM等级大于等于5<br/>
&emsp;&emsp;2. 商城购买物品通过调查问卷领取 延时在一分钟<br/>
&emsp;&emsp;3. 商城默认领取间隔为20分钟 <br/>
	修改 **AL-Game\config\main** 下的 **security.properties** 文件 
	修改
	```
        gameserver.security.survey.delay.minute = 20
	为
	gameserver.security.survey.delay.minute = 1
        ```
	这样购买的物品领取间隔为1分钟
&emsp;&emsp;4.启动商城后 浏览器访问 localhost:233 即可打开商城登录页面<br/>
&emsp;&emsp;5.商城币为游戏内的 **金功勋勋章** **id：186000030**  因为有延迟，所以获取金功勋章后需要小退才能同步到商城<br/>
&emsp;&emsp;6.商城币严格按照数据库数值计算，游戏内的**金功勋勋章数量**仅供参考<br/>

## 参与贡献

&emsp;&emsp;1. Fork 本仓库<br/>
&emsp;&emsp;2. 新建 Feat_xxx 分支<br/>
&emsp;&emsp;3. 提交代码<br/>
&emsp;&emsp;4. 新建 Pull Request<br/>

## 图片展示
###用户功能###
**主题更换**
![主题更换](https://images.gitee.com/uploads/images/2019/0721/220417_8d140c02_2252189.png "主题更换页")
**物品购买**
![物品购买](https://images.gitee.com/uploads/images/2019/0721/220527_a0a7d0ab_2252189.png "商品购买页.png")
**颜色选择**
![颜色选择](https://images.gitee.com/uploads/images/2019/0721/220612_3a26617e_2252189.png "颜色选择页")
**网页全屏**
![网页全屏](https://images.gitee.com/uploads/images/2019/0721/220716_8f5edfc2_2252189.png "全屏网页.png")
**物品检索**
![物品检索](https://images.gitee.com/uploads/images/2019/0721/220759_328d303b_2252189.png "永恒检索（技能 npc 套装 任务 物品）.png")
**便签功能**
![便签功能](https://images.gitee.com/uploads/images/2019/0721/220826_26bf77a9_2252189.png "便签（清除浏览器缓存后 保存的内容清空）.png")![购买历史](https://images.gitee.com/uploads/images/2019/0721/220851_59889069_2252189.png "获取物品历史.png")

###管理功能###
**物品管理**
![物品管理](https://images.gitee.com/uploads/images/2019/0721/220943_51f4855c_2252189.png "商城出售物品管理.png")
**商城检索**
![商城检索](https://images.gitee.com/uploads/images/2019/0721/221004_85c2a97e_2252189.png "商城搜索.png")
**用户管理**
![用户管理](https://images.gitee.com/uploads/images/2019/0721/221023_17f1a37a_2252189.png "用户管理.png")