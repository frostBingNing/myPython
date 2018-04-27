# -*- coding:utf-8 -*-
import requests
import json
import time
from prettytable import PrettyTable
from stations import stations
f1 =input("请输入出发站 \n")
theBegin = stations[f1]
f2 =input("请输入到达站 \n")
theEnd = stations[f2]

#print(theBegin + theEnd)

f3 = input("请输入出发日期 \n")
theDate = str(f3)

headers = {'user-agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36',
     'Accept':'image/webp,image/*,*/*;q=0.8',
     'Accept-Encoding':'gzip, deflate, sdch, br',
     'Accept-Language':'zh-CN,zh;q=0.8'
    }
def colored(color, text):
    table = {
        'red': '\033[91m',
        'green': '\033[92m',
        # no color
        'nc': '\033[0m'
    }
    cv = table.get(color)
    nc = table.get('nc')
    return ''.join([cv, text, nc])

def theUrl(theBegin,theEnd,theDate):
    thefullurl ="https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+ str(theDate) +"&leftTicketDTO.from_station="+str(theBegin) +"&leftTicketDTO.to_station="+str(theEnd)+"&purpose_codes=ADULT"
    return thefullurl
thefirsturl = theUrl(theBegin,theEnd,theDate)
#print(thefirsturl)
#https://kyfw.12306.cn/otn/leftTicket/queryO?leftTicketDTO.train_date=2018-03-26&leftTicketDTO.from_station=AYF&leftTicketDTO.to_station=XTQ&purpose_codes=ADULT
# https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2018-04-23&leftTicketDTO.from_station=AYF&leftTicketDTO.to_station=XTQ&purpose_codes=ADULT
theSecond = requests.get(thefirsturl,headers = headers)
rows = theSecond.json()['data']['result']
trains = PrettyTable()
trains.field_names=["车次","出发站","到达站","出发时间","到达时间","历时","  商务座  ","  一等座","  二等座 "," 软卧 "," 硬卧 "," 硬座 "," 无座 "]

the_count = len(rows)
print("一共有" + str(the_count) +"次列车")
print("现在的时间是 ：" + time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())))
print("请您稍微等待一会 您可以听听歌，喝口茶")
time.sleep(5)
the_begintime = time.time()
for row in rows :
    list = row.split("|")
    # the_first = stations[str(list[6])]
    # the_end = stations[str(list[7]]
    for key,values in stations.items():
        if stations[key] == list[6]:
            the_first = str(key)
        if stations[key] == list[7]:
            the_end = str(key)
    trains.add_row([list[3],the_first,the_end,list[8],list[9],list[10],list[32],list[31],list[30],list[23],list[28],list[29],list[26]])
    #time.sleep(20)
    #trains.add_column(list[3],list[6],list[7],list[8],list[9],list[10],list[32],list[31],list[30],list[24],list[28],list[29],list[26])
the_endtime  = time.time()
print(trains)
print("查询结束，一共花费了" + str(the_endtime - the_begintime) + " 秒")

# 到目前为止  遇到的问题是： 1：如何根据键值寻找键（在这里的例子就是字母转化为相应的地名） 2：为什么数字和地名连接不到一起
#   问题   1--已经成功解决了   问题  2 应该是一种逻辑上面的错误，分析一下择一匹配符的用法