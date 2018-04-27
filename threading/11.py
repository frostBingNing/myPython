import urllib.request
import re
import ssl
import requests
import os

host_name = "http://www.mm131.com/"
# key_names = ["xinggan", "qingchun", "xiaohua", "chemo", "qipao", "mingxing"]
key_names = ["qingchun","xinggan","xiaohua"]

import random

ssl._create_default_https_context = ssl._create_unverified_context
uapools = [
    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
    "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
    "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
    "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
    "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
    "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
    "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
    "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
    "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
]


def getDataByUrl(url):
    ua(uapools)
    data = urllib.request.urlopen(url).read().decode("gb2312", "ignore")
    return data


# 用户代理
def ua(uapools):
    thisua = random.choice(uapools)
    heaaders = ("User-Agent", thisua)
    opener = urllib.request.build_opener()
    opener.addheaders = [heaaders]
    urllib.request.install_opener(opener)


def getDataByKey(key_names):
    for keyname in key_names:
        for index in range(3):  # 每个大的类别获取3页的套图，每页现在是20组，总共每个大的类别获取60组
            url = host_name + keyname + "/"
            if index != 0:
                if keyname == "xinggan":
                    url += "list_6_" + str(index + 1) + ".html"
                elif keyname == "qingchun":
                    url += "list_1_" + str(index + 1) + ".html"
                elif keyname == "xiaohua":
                    url += "list_2_" + str(index + 1) + ".html"
                elif keyname == "chemo":
                    url += "list_3_" + str(index + 1) + ".html"
                elif keyname == "qipao":
                    url += "list_4_" + str(index + 1) + ".html"
                elif keyname == "mingxing":
                    url += "list_5_" + str(index + 1) + ".html"

            # print("url-->" + url)
            data = getDataByUrl(url)
            pat_link = '<dd><a target="_blank" href="(.*?)"><img src='
            pat_title = 'alt="(.*?)"\s{0,}width="'
            linkArray = re.compile(pat_link, re.S).findall(data)  # 获取链接的正则
            # titleArray = re.compile(pat_title, re.S).findall(data)  # 获取标题的正则
            getDataByLink(linkArray, keyname)


def getDataByLink(linkArray, keyName):
    for link in linkArray:
        data = getDataByUrl(link)
        pat_img = '<div class="content-pic"><a href=.{1,}src="(.*?)" /></a></div>'
        pat_title = '<h5>(.*?)</h5>'
        pat_pagecount = '<div class="content-page"><span class="page-ch">共(.*?)页'
        link_img = re.compile(pat_img, re.S).findall(data)
        title = re.compile(pat_title, re.S).findall(data)
        pageCounts = re.compile(pat_pagecount, re.S).findall(data)
        # print("==================================" + title[0] + "==============================")
        getDataByGroup(link, pageCounts, keyName, title[0])


def getImgUrlByLink(link, keyName, title, pageIndex):
    # print("每个高清图片所在的链接地址--->" + link)
    data = getDataByUrl(link)
    pat_img = '<div class="content-pic">\s{0,}<a href=.{1,}src="(.*?)" /></a></div>'
    imgUrl = re.compile(pat_img, re.S).findall(data)
    saveImgToLocal(imgUrl[0], keyName, title, pageIndex)
    # print(imgUrl)


# 获取每个组的套图链接地址
def getDataByGroup(link, pageCounts, keyName, title):
    for pageCount in pageCounts:
        # pageCount代表每个套图中有多少张高清图片
        # print("每套套图的高清图片数量:" + pageCount)
        for i in range(int(pageCount)):
            if i != 0:
                # 如果不是首页的话，就开始替换字符串～
                newLink = link.replace(".html", "_" + str(i + 1) + ".html")
                getImgUrlByLink(newLink, keyName, title, i)
            else:
                getImgUrlByLink(link, keyName, title, i)
                pass
            pass


# 下载图片到本地，改用了requests库
# keyName: 大的类别关键字，xinggan,qingchun,xiaohua
# title: 每个套图的标题
# pageIndex: 每个套图中高保真图所在的索引号
def saveImgToLocal(imgUrl, keyName, title, pageIndex):
    path = "mm/" + keyName + "/" + title + "/"
    if not os.path.isdir(path):
        os.makedirs(path)
    path += title + str(pageIndex + 1) + '.jpg'
    try:
        headers = {'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
                   'Accept-Encoding': 'gzip, deflate',
                   'Accept-Language': 'zh-CN,zh;q=0.8',
                   'Cache-Control': 'no-cache',
                   'Connection': 'keep-alive',
                   'Cookie': 'UM_distinctid=15fa02251e679e-05c01fdf7965e7-5848211c-144000-15fa02251e7800; bdshare_firstime=1510220189357; CNZZDATA1263415983=1653134122-1510216223-null%7C1510216223; CNZZDATA3866066=cnzz_eid%3D376479854-1494676185-%26ntime%3D1494676185; Hm_lvt_9a737a8572f89206db6e9c301695b55a=1510220189; Hm_lpvt_9a737a8572f89206db6e9c301695b55a=1510220990',
                   'Host': 'img1.mm131.me',
                   'Pragma': 'no-cache',
                   'Referer': 'http://www.mm131.com/' + keyName + "/",
                   'User-Agent': random.choice(uapools)}
        pic = requests.get(imgUrl, headers=headers, timeout=10)
        # urllib.request.urlretrieve(imgUrl, path)
        print(keyName + '下的' + title + '中的第' + str(pageIndex + 1) + '张图片下载完成-->' + imgUrl)
    except requests.exceptions.ConnectionError:
        print(keyName + '下的' + title + '中的第' + str(pageIndex + 1) + '张图片下载【错误】------------')

    fp = open(path, 'wb')
    fp.write(pic.content)
    fp.close()


# print(getDataByUrl("http://www.mm131.com/chemo/1594_4.html"))
getDataByKey(key_names)