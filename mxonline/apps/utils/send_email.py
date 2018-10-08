# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/17 22:30
"""

# 之前的邮箱表单--- 当用户点击激活后，判断是否正确点击
from apps.users.models import EmailVerifyRecord
from random import Random
from django.core.mail import send_mail
from django.http import HttpResponse

from mxonline.settings import EMAIL_FROM

def random_string(min_length = 8):
    my_str = ""
    theString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz"
    count = len(theString) - 1
    random = Random()
    for i in range(min_length):
        my_str+= theString[random.randint(0,count)]
    return my_str



# email 就是接受邮件的那些对象
def send_register_email(email,send_type="register"):
    email_record = EmailVerifyRecord() # 实例化一个对象来进行预处理
    # 邮箱重新绑定的验证码 目前设定为4位
    if send_type == 'update_email':
        code = random_string(4)
    else:
        code = random_string(16)
    email_record.code  = code
    email_record.email  = email
    email_record.send_type = send_type
    email_record.save()

    # 下面就是构造邮件的信息
    if send_type == "register":
        email_title = "暮学网用户注册激活链接"
        email_body = "  请点击右边链接进行激活 ---> http://127.0.0.1:8000/active/{0}".format(code)
        # 邮件内容制作完成
        # 使用Django内置函数完成邮件发送。四个参数：主题，邮件内容，从哪里发，接受者list
        send_status = send_mail(email_title,email_body,EMAIL_FROM,[email]) # 返回布尔值
        if send_status:
            pass

    elif send_type ==  "forget":
        email_title = "暮学网用户重置密码确认链接"
        email_body = "  请点击右边链接进行确认 ---> http://127.0.0.1:8000/reset/{0}".format(code)
        # 邮件内容制作完成
        # 使用Django内置函数完成邮件发送。四个参数：主题，邮件内容，从哪里发，接受者list
        send_status = send_mail(email_title, email_body, EMAIL_FROM, [email])  # 返回布尔值
        if send_status:
            pass

    elif send_type == 'update_email':
        email_title = "慕学网用户邮箱重新绑定验证码"
        email_body = " 请采用右边的验证码 --> {0}".format(code)
        # 邮件内容制作完成
        # 使用Django内置函数完成邮件发送。四个参数：主题，邮件内容，从哪里发，接受者list
        send_status = send_mail(email_title, email_body, EMAIL_FROM, [email])  # 返回布尔值
        if send_status:
            pass
