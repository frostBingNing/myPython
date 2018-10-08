# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/20 23:26
"""
import re
from django import forms
from apps.operation.models import  UserAsk


class UserAskForm(forms.ModelForm):
    # 构建一个form --- 处理数据
    class Meta:
        # extends 的数据表
        model = UserAsk
        # 采用的字段名称
        fields = ['name','mobile','course_name']
        # 在这里就可以进行手机号码 或者其他数据字段的正误判断
        # 函数的命名 必须是  ---- clean_字段名


    # 手机验证操作

    # 必须按照clean_ 开头来命名
    def clean_mobile(self):
        mobile = self.cleaned_data['mobile']  # 从表单里面获取 mobile 字段
        REGEX_MOBILE = "^1[358]\d{9}|^147\d{8}|^176\d{8}$"
        p = re.compile(REGEX_MOBILE)
        # 进行预编译
        if p.match(mobile): # match 匹配字符串
            return mobile
            # pass
        # 否则就把相关的错误 raise
        else:
            raise forms.ValidationError("手机号码非法",code="mobile_invalid")