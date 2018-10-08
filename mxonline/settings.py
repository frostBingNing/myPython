"""
Django settings for mxonline project.

Generated by 'django-admin startproject' using Django 2.1.1.

For more information on this file, see
https://docs.djangoproject.com/en/2.1/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/2.1/ref/settings/
"""

import os
import sys

# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
# 添加额外信息的整体环境路径  方便系统寻找相应的信息！！！
sys.path.insert(0,os.path.join(BASE_DIR,"apps"))
sys.path.insert(1,os.path.join(BASE_DIR,"extra_apps"))


# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/2.1/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = 'haosy587xh85uu64ydif2r0dn*c$n%%x5=5efh^%@odtg0j#sp'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True
# DEBUG = False
# 生产模式 下面的host必须配置
ALLOWED_HOSTS = []
# ALLOWED_HOSTS = ['*']

AUTHENTICATION_BACKENDS = (
    'apps.users.views.CustomBackend',
)

# Application definition

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',

    # 每次设计一个app 都需要进行相应的注册
    'apps.users',
    'apps.organization',
    'apps.operation',
    'apps.courses',

    # 另一种后台管理系统  xadmin
    'xadmin',
    'crispy_forms',

    # 配置验证码第三方插件
    'captcha',
    # 导入有关页面信息分页的第三方插件
    'pure_pagination',

    # 第三方文本编辑插件 
    'DjangoUeditor',  # 在集成的时候修改了C:\Python36\Lib\site-packages\django\forms\boundfield.py 里面93代码

]


# 直接调用该模块 -- 进行相应的设计
AUTH_USER_MODEL = "users.UserProfile"



MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'mxonline.urls'

# 分页信息的默认配置  后面就可以进行调用
PAGINATION_SETTINGS = {
    'PAGE_RANGE_DISPLAYED': 10,
    'MARGIN_PAGES_DISPLAYED': 2,
    'SHOW_FIRST_PAGE_WHEN_INVALID': True,
}


TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [os.path.join(BASE_DIR, 'templates')]
        ,
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
                # media 的内部处理类
                'django.template.context_processors.media',
            ],
        },
    },
]

WSGI_APPLICATION = 'mxonline.wsgi.application'


# Database
# https://docs.djangoproject.com/en/2.1/ref/settings/#databases


# 工程的第一步   就是创建数据库的链接
DATABASES = {
    'default': {
        # 'ENGINE': 'django.db.backends.sqlite3',
        # 'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
        'ENGINE': 'django.db.backends.mysql',
        'NAME': "mxonline",
        'USER': 'root',
        'PASSWORD': '123456',
        'PORT': '3306',
        'HOST': 'localhost',
    }
}


# Password validation
# https://docs.djangoproject.com/en/2.1/ref/settings/#auth-password-validators

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]


# Internationalization
# https://docs.djangoproject.com/en/2.1/topics/i18n/

LANGUAGE_CODE = 'zh-Hans'

TIME_ZONE = 'Asia/Shanghai'

USE_I18N = True

USE_L10N = True

# 修改成本地时间
USE_TZ = False

# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/2.1/howto/static-files/

STATIC_URL = '/static/'

# 切记加上 DIRS
STATICFILES_DIRS = (
    os.path.join(BASE_DIR,"static"),
)

# 邮箱相关的注册
EMAIL_HOST = "smtp.sina.cn"
EMAIL_POST = 25
EMAIL_HOST_USER = "17369201174@sina.cn"
EMAIL_HOST_PASSWORD = "983635lxd"
EMAIL_TLS = False
EMAIL_FROM = "17369201174@sina.cn"


# 上传文件路径--- 根目录下面的media  保存上传文件
MEDIA_URL = "/media/"
MEDIA_ROOT = os.path.join(BASE_DIR,'media')
# STATIC_ROOT = os.path.join(BASE_DIR,'static')