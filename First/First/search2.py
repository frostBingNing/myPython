# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/1219:36
"""

from django.shortcuts import render
from django.views.decorators import csrf # 防止伪装提交请求


def Post_form(request):
    content = {}
    if request.POST:
        content['test'] = request.POST['q']
    return render(request,'post_form.html',content)