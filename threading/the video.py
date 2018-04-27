# -*- coding : utf-8 -*-
import threading # thread
from time import sleep,ctime # time
from lxml import etree # xpath
import requests
import os
odd_url = "http://www.mm131.com/qingchun/3059"
# 需要下载的图片
targets = []  # 无限制
# 自动生成目标列表
# 下面这一行   是pixiv 网站获得图片需要的请求
# referer_url="https://www.pixiv.net/member_illust.php?mode=medium&illust_id"
headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36',
            'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
                   'Accept-Encoding': 'gzip, deflate',
                   'Accept-Language': 'zh-CN,zh;q=0.8',
                    'Cache-Control': 'no-cache',
                    'Connection': 'keep-alive',
                    'Cookie': 'BAIDUID=DC7EBA041292ED3C052B16B62789E178:FG=1; BIDUPSID=DC7EBA041292ED3C052B16B62789E178; PSTM=1524647952; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; __cfduid=d5834c7257a48877dad70515b3660469b1524649638; H_PS_PSSID=1431_21079_18559_26184_20928; PSINO=7; BDRCVFR[dG2JNJb_ajR]=mk3SLVN4HKm; BDRCVFR[X_XKQks0S63]=mk3SLVN4HKm; BDRCVFR[-pGxjrCMryR]=mk3SLVN4HKm; pgv_pvi=3175512064; pgv_si=s8014817280',
                    'Host': 'img1.mm131.me',
                    'Pragma': 'no-cache',
                   'Referer': 'http://www.mm131.com/'
           }
def beingToTargets(url):
    the_text = requests.get(url).content
    # <class 'requests.models.Response'>
    html = etree.HTML(the_text)
    links = html.xpath('//div[@class="content-pic"]/a/img')
    target = links[0].get('src')
    targets.append(target)
# 51
def makeUrl():
    for i in range(51):
        if i == 0:
            the_new = odd_url + ".html"
            beingToTargets(the_new)
        else:
            the_new = odd_url + "_" +str(i+1) +".html"
            beingToTargets(the_new)
# being to 51 counts pic


# http://img1.mm131.me/pic/3059/9.jpg
# http://img1.mm131.me/pic/3059/44.jpg
# http://img1.mm131.me/pic/3059/45.jpg
# http://img1.mm131.me/pic/3059/46.jpg
# http://img1.mm131.me/pic/3059/47.jpg
# http://img1.mm131.me/pic/3059/48.jpg
# http://img1.mm131.me/pic/3059/49.jpg
# http://img1.mm131.me/pic/3059/50.jpg
# http://img1.mm131.me/pic/3059/51.jpg
# targets[51]
threadlock = []

def the_zhu(num,threadName,count):
    print(" the %s start at %s" %(threadName,ctime()))
    sleep(count)
    download(targets[num],num)
    print("  done the %s at %s" %(threadName,ctime()))

def download(urls,number):
    th = requests.get(urls,headers = headers)
    # th1 = th.content
    f = open("D:/test" + "/" + str(number) + ".jpg" ,"wb")
    f.write(th.content)
    f.close()

def main():
    for i in range(28):
        t = threading.Thread(target=the_zhu,args=(i,"thread--1",3))
        threadlock.append(t)

    for i in range(28,51):
        t = threading.Thread(target=the_zhu,args=(i,"thread--2",4))
        threadlock.append(t)

    Length = len(threadlock)

    for i in range(Length):
        threadlock[i].start()

    for i in range(Length):
        threadlock[i].join()

    print("  all have done at %s" %ctime())

if __name__ == '__main__':
    makeUrl()
    main()
