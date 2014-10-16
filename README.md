云社区 android 客户端
=====================

测试地址:http://yuncommunity.com/
---------------------------------------------------
文档地址:https://www.zybuluo.com/oldfeel/note/21671
---------------------------------------------------
开发需要的library:https://github.com/oldfeel/AndroidUtils
-----------------------------------------------------

## 简介 ##

### 关于我 ###

专注android应用开发900天

### 我想要做什么 ###

将社区放入云端,认识自己社区内的所有人,可通过网络获取社区周边的所有开放信息.

### 为什么我需要你的支持及资金用途 ###

每个社区都有自己的特色.为每个社区创建适合自己的app需要大量的人力物力.

### 我的承诺与回报 ###

一个自由开放的生活环境和股份.

### 可能存在的风险 ###

信息过于开放引发刑事案件,导致项目被关闭.


## 修改记录 ##

## 目录 ##

[TOC]

## 编码规范 ##

  - 所有类名/变量名不能用中文拼音
  - 资源文件id用小写英文字母表示,多个单词用\_分割,例如 home_search
  - 变量/方法命名规则符合驼峰命名法,首字母小写,后续单词首字母大写,例如homeSearch
  - view变量名以view单词首字母缩写开头,功能名做后缀,例如ivSearch(是个ImageView),lvQuestion(是个ListView)
  - activity首字母大写,后面符合驼峰命名法.
  - 每个类要添加注释,简单介绍下这个类的作用.
  - *.xml图片要让如/res/drawable文件夹下.

## 项目包结构 ##

  - com.yuncommunity
    - 应用的包名,activity都放在这个位置.
  - com.yuncommunity.adapter
    - 列表适配器
  - com.yuncommunity.app
    - 应用的配置及管理文件
  - com.yuncommunity.base
    - 封装的一些基类
  - com.yuncommunity.db
    - 数据库帮助类
  - com.yuncommunity.fragment
    - fragment
  - com.yuncommunity.item
    - 列表中的item对象
  - com.yuncommunity.list
    - 列表
  - com.yuncommunity.receiver
    - 广播接收器
  - com.yuncommunity.service
    - 应用后台运行的service
  - com.yuncommunity.util
    - 用来的一些工具类
  - com.yuncommunity.view
    - 自定义的view

## 接口规范 ##

  - 成功时返回result:true和data:{}或者data:[]
    {
    "result": true, 
    "data": { }
    }
    {
    "result": true, 
    "data": [ ]
    }
  - 失败时返回result:false和message:(错误原因)
    {
    "result": false, 
    "message": "帐号或密码错误"
    }

## 用户信息 ##

### 登录 ###

  - 请求方法    login
  - 发送参数
    - email                         用户邮箱
    - password                      用户密码
  - 返回结果

### 注册 ###

  - 请求方法    register
  - 发送参数
    - email                         用户邮箱
    - password                      用户密码
    - communityid                   社区id
  - 返回结果

### 获取个人资料 ###

  - 请求方法    user_info
  - 发送参数
    - userid                        用户id
    - targetid                      目标用户id
  - 返回结果

### 更新用户信息 ###

  - 请求方法    update_user_info
  - 发送参数
    - userid                        用户id
    - name                          用户名
    - password                      用户密码
    - phone                         用户电话
    - housenumber                   房间号码
    - birthday                      生日
    - permission                    权限(权限,0为仅对关注对象开放,1为对小区认证用户开放,2为完全保密,3为公开)
    - background                    个人资料背景图片
    - avatar                        头像
    - friendmsg                     true为开启朋友信息提醒,false为关闭
    - activitymsg                   true为开启活动信息提醒,false为关闭
    - businessmsg                   true为开启商家信息提醒,false为关闭
    - introduction                  简介
  - 返回结果

## 社交 ##

### 关注/取消关注 ###

  - 请求方法    user_following
  - 发送参数
    - userid                        用户id
    - targetid                      目标用户id
    - isfollowing                   true为关注用户,false为取消关注
  - 返回结果

### 用户的粉丝 ###

  - 请求方法    user_fans
  - 发送参数
    - userid                        用户id
    - targetid                      目标用户id
    - page                          页码
  - 返回结果

### 用户关注的用户 ###

  - 请求方法    user_followings
  - 发送参数
    - userid                        用户id
    - targetid                      目标用户id
    - page                          页码
  - 返回结果

### 指定用户参与的信息列表 ###

  - 请求方法    user_information_list
  - 发送参数
    - userid                        用户id
    - page                          页码
    - infotype                      信息类型,1为活动,2为商家服务,3为个人服务
  - 返回结果

### 指定用户发布的信息列表 ###

  - 请求方法    user_release_list
  - 发送参数
    - userid                        用户id
    - page                          页码
  - 返回结果

### 获取用户好友的最近动态,关注/评论/赞同/反对 ###

  - 请求方法    user_friend_dynamic
  - 发送参数
    - userid                        用户id
    - page                          页码
  - 返回结果

### 聊天记录 ###

  - 请求方法    chat_history
  - 发送参数
  - 返回结果

## 系统 ##

### 建议反馈 ###

  - 请求方法    feedback
  - 发送参数
    - userid                        用户id
    - content                       反馈内容
    - anonymous                     true为匿名,false为不匿名
  - 返回结果

### 检查版本 ###

  - 请求方法    check_version
  - 发送参数
    - communityid                   社区id
  - 返回结果

## 信息相关 ##

### 发布信息 ###

  - 请求方法    information_release
  - 发送参数
    - userid                        用户id
    - title                         标题
    - description                   描述
    - address                       地址
    - lon                           经度
    - lat                           纬度
    - phone                         电话
    - tags                          标签(多个标签用逗号隔开)
    - image                         图片
    - voice                         录音
    - video                         视频
    - infotype                      信息类型,1为活动,2为商家服务,3为个人服务
  - 返回结果

### 活动/商家服务/个人服务详情 ###

  - 请求方法    information_detail
  - 发送参数
    - userid                        用户id
    - informationid                 信息id
  - 返回结果

### 信息列表 ###

  - 请求方法    information_list
  - 发送参数
    - page                          页码
    - infotype                      信息类型
  - 返回结果

### 关注该信息的用户列表 ###

  - 请求方法    information_followers
  - 发送参数
    - userid                        用户id
    - informationid                 信息id
    - page                          页码
  - 返回结果

### 活动与用户之间的关注 ###

  - 请求方法    information_following
  - 发送参数
    - userid                        用户id
    - informationid                 信息id
    - isfollowing                   true为关注信息,false为取消关注
  - 返回结果

### 举报 ###

  - 请求方法    report
  - 发送参数
    - userid                        用户id
    - informationid                 信息id
    - content                       举报内容
  - 返回结果

## 评论相关 ##

### 评论列表 ###

  - 请求方法    information_commentlist
  - 发送参数
    - page                          页码
    - informationid                 信息id
    - userid                        用户id
  - 返回结果

### 评论 ###

  - 请求方法    information_comment
  - 发送参数
    - userid                        用户id
    - informationid                 信息id
    - content                       评论内容
    - score                         评分
    - tags                          标签(多个标签用逗号隔开)
  - 返回结果

### 删除评论 ###

  - 请求方法    information_commentdelete
  - 发送参数
    - userid                        用户id
    - informationid                 信息id
  - 返回结果

### 赞 ###

  - 请求方法    comment_approval
  - 发送参数
    - userid                        用户id
    - commentid                     评论id
    - isapproval                    true为赞,false为取消赞
  - 返回结果

### 反对 ###

  - 请求方法    comment_opposition
  - 发送参数
    - userid                        用户id
    - commentid                     评论id
    - isopposition                  true为反对,false为取消反对
  - 返回结果

## 商家服务 ##

### 产品列表 ###

  - 请求方法    product_list
  - 发送参数
    - userid                        用户id
    - informationid                 信息id
  - 返回结果

### 添加产品 ###

  - 请求方法    product_add
  - 发送参数
    - informationid                 商家id
    - name                          产品名称
    - description                   简介
    - image                         图片
  - 返回结果

### 删除产品 ###

  - 请求方法    product_delete
  - 发送参数
    - userid                        用户id
    - productid                     产品id
  - 返回结果

## 个人服务 ##

## 活动 ##

### 活动报名 ###

  - 请求方法    activity_sign_up
  - 发送参数
    - userid                        用户id
    - informationid                 活动id
    - name                          姓名
    - phone                         电话
    - email                         邮箱
    - remark                        备注
    - adult                         成人数量
    - child                         孩子数量
  - 返回结果

### 取消活动报名 ###

  - 请求方法    activity_sign_up_cancel
  - 发送参数
    - userid                        用户id
    - informationid                 活动id
  - 返回结果

### 查看活动报名者列表 ###

  - 请求方法    activity_sign_up_list
  - 发送参数
    - informationid                 活动id
    - page                          页码
  - 返回结果

## 其他 ##

### 获取上传文件需要的uptoken ###

  - 请求方法    uptoken
  - 发送参数
    - 无
  - 返回结果

### 小区简介 ###

  - 请求方法    community_introduction
  - 发送参数
    - communityid                   小区id
  - 返回结果

### 编辑小区介绍 ###

  - 请求方法    community_edit
  - 发送参数
    - communityid                   小区id
    - description                   简介
    - image                         图片
    - lat                           纬度
    - lon                           经度
  - 返回结果
