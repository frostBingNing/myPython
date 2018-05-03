# -*- coding:utf-8 -*-
import requests
import re


# 还是需要浏览器伪装一下   然后就可以绕过去进行相应的操作了
headers ={
    'accept-encoding': 'gzip, deflate, br',
    'accept-language': 'zh-CN,zh;q=0.9',
    'if-modified-since': 'Fri, 30 Mar 2018 10:56:20 GMT',
    'referer': 'https://v.qq.com/',
    'user-agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36'
}

a1 = "https://video.coral.qq.com/varticle/2523226755/comment/v2?callback=_varticle2523226755commentv2&orinum=10&oriorder=o&pageflag=1&cursor="
num = "6384376673361994078"
a2 = "&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=9&_=1525336853625"

# 就是这样的思路 就可以执行很多遍了  中间变量发生替换即可
for i in range(3):
    url = a1 + num + a2
    print(url)
    th = requests.get(url, headers=headers)
    text = th.text
    next_page = re.findall('"last":.+?"', text)
    next_num = next_page[0].split(":")
    num = next_num[1][1:4] + next_num[1][4:-1]
    lists = re.findall('"content":.+?"',text)
    # next_page = re.findall('"last":.+?"',text)
    # print(next_page[0])
    for t in lists:
        try:
            t = t.split(":")
            th = t[1].encode("latin-1").decode("unicode_escape")
            print(th)
                #  下面那些没有成功加载的 是因为含有特殊符号。。。
        except UnicodeEncodeError as e:
            print("---里面含有特殊符号---")
            continue
    print(" 第 %d 页加载完毕 "  %(i+1))

# 至此  就是腾讯网有关影视的评论详情。。。。