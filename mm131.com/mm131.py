# -*- coding : utf-8 -*-
import requests
from time import sleep,ctime
from lxml import etree
import threading
import os
headers = {         'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36',
                    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
                   'Accept-Encoding': 'gzip, deflate',
                   'Accept-Language': 'zh-CN,zh;q=0.8',
                    'Cache-Control': 'no-cache',
                    'Connection': 'keep-alive',
                    'Cookie': 'BAIDUID=DC7EBA041292ED3C052B16B62789E178:FG=1; BIDUPSID=DC7EBA041292ED3C052B16B62789E178; PSTM=1524647952; HMACCOUNT=B9EABC9496EE1E77; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; __cfduid=d5834c7257a48877dad70515b3660469b1524649638; H_PS_PSSID=1431_21079_18559_26184_20928; PSINO=7',
                    # 'Host': 'img1.mm131.me',
                    'Pragma': 'no-cache',
                    'Referer': 'http://www.mm131.com'
           }
# 用到的请求头
def makedic(path):
    isExists = os.path.exists(path)
    if not isExists:
        os.mkdir(path)
        print(path + "创建成功")
        return True
    else :
        print(path + "重复了，所以不再创建")
        return False
all_title = ["xinggan","qingchun","xiaohua","chemo"]
all_pages = []
def get_all_pages():
    for i in range(len(all_title)):
        first_url = "http://www.mm131.com/" + all_title[i] + "/"
        th = requests.get(first_url,headers = headers).content
        text = etree.HTML(th)
        totalpages = text.xpath('//div[@class="main"]/dl//dd[21]//a')
        totalpages = totalpages[len(totalpages)-1].get("href")
        if i == 0:
            bigpages = totalpages[7:10]
        elif i==1:
            bigpages = totalpages[7:9]
        elif i==2:
            bigpages = totalpages[7:8]
        else :
            bigpages = totalpages[7:9]
        all_pages.append(int(bigpages))

# all_pages =[139,31,6,10]
# 这是每个分栏的页面数量
now_pic_name = []  # 保存当前page的名称
now_pic_url = []  # 保存当前page的url
def each_pages():
    for i in range(len(all_pages)):
        # each_albums():
        add_path = all_title[i]
        odd_url = "http://www.mm131.com/" + add_path + "/"
        if i ==0:
            add_next ="list_6_"
            add_html =".html"
        elif i==1:
            add_next = "list_1_"
            add_html = ".html"
        elif i==2:
            add_next = "list_2_"
            add_html = ".html"
        else:
            add_next = "list_3_"
            add_html = ".html"
        for t in range(all_pages[i]):
            if t == 0:
                th1 = requests.get(odd_url,headers= headers).content
                th1 = etree.HTML(th1)
                links = th1.xpath('//div[@class="main"]/dl//dd')
                # 每页的数量
                page_count = len(links) - 1
                for z in range(page_count):
                    pic_url = th1.xpath('//div[@class="main"]/dl//dd['+str(z+1)+']/a')
                    new_pic_url = pic_url[0].get('href')
                    now_pic_url.append(new_pic_url)
            else:
                odd_urls = odd_url + add_next + str(t+1) + add_html
                th1 = requests.get(odd_urls, headers=headers).content
                th1 = etree.HTML(th1)
                links = th1.xpath('//div[@class="main"]/dl//dd')
                # 每页的数量
                page_count = len(links) - 1
                for q in range(page_count):
                    pic_url = th1.xpath('//div[@class="main"]/dl//dd[' + str(q + 1) + ']/a')
                    new_pic_url = pic_url[0].get('href')
                    now_pic_url.append(new_pic_url)
    # print(len(now_pic_url))3679  更新后的 2018-4-29-21-20
    # 所有图片加载完毕

def download(threadingname,m):
    # for m in range(len(now_pic_url)):
        print(" now  threading %s is running" %(threadingname))
        url2 = now_pic_url[m]
        print(url2)
        th2 = requests.get(url2, headers=headers).content
        th2 = etree.HTML(th2)
        attr = th2.xpath('//div[@class="content"]/div[3]/span[1]')
        # 可能有的少 没有双位数
        if len(attr[0].text) == 4:
            count_pages = attr[0].text[1:3]
        else:
            count_pages = attr[0].text[1:2]
        # count_pages problems text[1:2]
        package = th2.xpath('//div[@class="content"]/h5')[0].text
        for n in range(int(count_pages)):
            if n == 0:
                url_2 = url2
                th3 = requests.get(url_2,headers=headers).content
                th31 = etree.HTML(th3)
                links2 = th31.xpath('//div[@class="content"]/div[2]/a/img')
                true_pic = links2[0].get('src')
            else:
                if len(url2) == 38:
                    url_2 = url2[:33] + "_" + str(n + 1) + ".html"
                else:
                    url_2 = url2[:32] + "_" + str( n + 1) + ".html"
                th3 = requests.get(url_2, headers=headers).content
                th31 = etree.HTML(th3)
                links2 = th31.xpath('//div[@class="content"]/div[2]/a/img')
                true_pic = links2[0].get('src')
            pics = requests.get(true_pic,headers=headers).content
            print("----- 正在下载 %s -- 一共 %s 页" %(url_2,count_pages))
            nameid = th31.xpath('//div[@class="content"]/h5')[0].text
            path = "F:/mm131/" + package
            makedic(path)
            f = open("F:/mm131/" + package + "/" + nameid +".jpg" ,"wb")
            f.write(pics)
            f.close()
        print("---- 当前页面保存完毕 ---- ")

def run_thread(thenumber,theid):
    threadlocks = []
    add_number = theid*200  # 偏移量
    print("----the thread %s starts at %s" %(theid,ctime()))
    for i in range(thenumber):
        threadlocks.append(threading.Thread(target=download, args=("thread---" + str(theid), i+add_number)))
    Length = len(threadlocks)
    for i in range(Length):
        threadlocks[i].start()
    for i in range(Length):
        threadlocks[i].join()
    print("thread %s ends at %s" % (theid,ctime()))
    sleep(5)
def main():
    get_all_pages()
    each_pages()
    # 200个线程每次 数目少的例外
    # for i in range(19):
    #     if i != 18:
    #         run_thread(200,i)
    #     else:
    #         run_thread(79,i)
    for i in range(5):
        run_thread(100,i)
    print("***** all have done at %s *****" %(ctime()))
if __name__ == '__main__':
    main()

# 基本功能算是实现了，个别有些问题，但是语法确实没错，就暂且视为最后的版本喽
# search mm131.com


 # print(n)  之所以报错 是因为 中间url2 的数值改变了

# 下面这一部分是对全站的图片进行分类爬取 但是效果并不是很好
             # if url2[21:23] == "xi":
                #     if len(url2) == 38:
                #         url_2 = url2[:33] + "_" + str(n + 1) + ".html"
                #     else:
                #         url_2 = url2[:32] + "_" + str( n + 1) + ".html"
                #     th3 = requests.get(url_2, headers=headers).content
                #     th31 = etree.HTML(th3)
                #     links2 = th31.xpath('//div[@class="content"]/div[2]/a/img')
                #     true_pic = links2[0].get('src')
                # elif url2[21:23]== "qi":
                #     if len(url2) == 39:
                #         url_2 = url2[:34] + "_" + str( n + 1) + ".html"
                #     elif len(url2) == 38:
                #         url_2 = url2[:33] + "_" + str( n + 1) + ".html"
                #     elif len(url2) == 37:
                #         url_2 = url2[:32] + "_" + str( n + 1) + ".html"
                #     else:
                #         url_2 = url2[:31] + "_" + str( n + 1) + ".html"
                # elif url2[21:23] == "xi" and url2[23:24] == 'a':
                #     if len(url2) == 38:
                #         url_2 = url2[:33] + "_" + str( n + 1) + ".html"
                #     elif len(url2) == 37:
                #         url_2 = url2[:32] + "_" + str( n + 1) + ".html"
                #     elif len(url2) == 36:
                #         url_2 = url2[:31] + "_" + str( n + 1) + ".html"
                #     else:
                #         url_2 = url2[:30] + "_" + str( n + 1) + ".html"
                # else:
                #     if len(url2) == 34:
                #         url_2 = url2[:29] + "_" + str( n + 1) + ".html"
                #     elif len(url2) == 35:
                #         url_2 = url2[:30] + "_" + str( n + 1) + ".html"
                #     elif len(url2) == 36:
                #         url_2 = url2[:31] + "_" + str( n + 1) + ".html"

# 编程过程中遇到的问题：
# 1 --- list index out of range   中间ulr2 原始地址发生了不正确的修改
#     导致后面没有办法寻找到正确的地址

# 2 --- threading 不可以一次性创建太多个  资源有限

# 3 --- 要想正确的获得服务器上面的图片 就需要进行反防盗图
#    就是添加在请求头里面  referer 注明访问的来源  就可以正确访问图片了

