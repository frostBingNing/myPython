# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/16 18:30
"""
from django import forms
from captcha.fields import CaptchaField
from apps.users.models import  UserProfile
# 继承views 来完成form验证


class RegisterForms(forms.Form):
    email = forms.EmailField(required=True)
    password = forms.CharField(required=True, min_length=6)
    captcha = CaptchaField(error_messages={'invalid':'验证码错误'})


class LoginForms(forms.Form):
    # 定义form类
    username = forms.CharField(required=True)
    # min_length 限制当前的字段必须输入相应的字符数
    password = forms.CharField(required=True,min_length=6)



class ForgetForms(forms.Form):
    email = forms.EmailField(required=True)
    captcha = CaptchaField(error_messages={'invalid': '验证码错误'})


class UploadImageForms(forms.ModelForm):
    class Meta:
        model = UserProfile
        fields = ['image']


# 个人中心重置密码的验证表
class UpdatePassForms(forms.Form):

    password1 = forms.CharField(required=True,min_length=6,max_length=20)
    password2 = forms.CharField(required=True,min_length=6,max_length=20)

# 添加个人信息页面的form表单
class UserInforForms(forms.ModelForm):

    class Meta:
        model = UserProfile
        fields = ['nickname','birday','gender','address','mobile']
