#  -*- coding : utf-8 -*-
import hashlib

def get_md5(url):
    '''TypeError: Unicode-objects must be encoded before hashing'''

    if isinstance(url,str):
        url = url.encode()
    m = hashlib.md5()
    m.update(url)
    return m.hexdigest()

# if __name__ == '__main__':
#     print(get_md5('http://www.baidu.com'))