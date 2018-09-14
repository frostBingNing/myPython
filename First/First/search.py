# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/1219:10
"""

from django.http import HttpResponse
from django.shortcuts import render_to_response


def search_form(request):
    return render_to_response('search_form.html')

def search(request):
    request.encoding = 'utf8'
    if 'q' in request.GET:
        message = "当前搜索的内容是 : " + request.GET['q']
    else:
        message = "当前搜索为空"
    return HttpResponse(message)