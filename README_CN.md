# 知乎 Android 逆向工程解决方案

一个用于知乎 Android 应用的复杂逆向工程解决方案，用于计算 API 认证所需的 `x-zse-96` 参数。该项目提供了绕过知乎签名验证系统的完整实现。

## 🌐 官方链接

- **官方网站**: [https://lostjay.xyz/gitea/github/zhihu_android_crawler](https://lostjay.xyz/gitea/github/zhihu_android_crawler)
- **GitHub 镜像**: [https://github.com/lostjay/zhihu_android_crawler](https://github.com/lostjay/zhihu_android_crawler)

[English](README.md) | [中文文档](README_CN.md)

## 🚀 功能特性

- **签名生成**: 计算知乎 API 请求的 `x-zse-96` 参数
- **Unidbg 集成**: 使用 Unidbg 模拟 Android 原生库
- **RESTful API 服务器**: 提供签名生成的 HTTP 端点
- **Python 客户端**: 易于使用的知乎 API 交互 Python 包装器
- **全面测试**: 所有主要功能的单元测试

## 📋 系统要求

### 系统需求
- **Java 11+** (用于 Unidbg 和 Maven)
- **Python 3.10+** (用于 Python 客户端)
- **Maven 3.6+** (用于构建 Java 组件)
- **Git** (用于克隆仓库)

## 🔧 安装

### 第一步：GPG 密钥设置（Maven 必需）

#### 检查现有 GPG 密钥
```bash
gpg --list-secret-keys
```

#### 生成新的 GPG 密钥（如需要）
```bash
# 生成新的 GPG 密钥对
gpg --gen-key

# 按照提示操作：
# 1. 选择 "RSA and RSA" (默认)
# 2. 选择 4096 位以获得最大安全性
# 3. 选择 0 (永不过期)
# 4. 输入您的姓名和邮箱
# 5. 输入密码短语（推荐）
```

#### 配置 Maven 的 GPG 密钥

1. **导出您的 GPG 密钥 ID**:
```bash
gpg --list-secret-keys --keyid-format LONG
# 查找类似这样的行: sec   rsa4096/ABC123DEF4567890 2023-01-01 [SC]
# 密钥 ID 是: ABC123DEF4567890
```

2. **创建 Maven settings.xml** (如果不存在):
```bash
mkdir -p ~/.m2
touch ~/.m2/settings.xml
```

3. **在 settings.xml 中添加 GPG 配置**:
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              http://maven.apache.org/xsd/settings-1.0.0.xsd">
    
    <profiles>
        <profile>
            <id>gpg</id>
            <properties>
                <gpg.executable>gpg</gpg.executable>
                <gpg.keyname>YOUR_GPG_KEY_ID_HERE</gpg.keyname>
                <gpg.passphrase>YOUR_GPG_PASSPHRASE_HERE</gpg.passphrase>
            </properties>
        </profile>
    </profiles>
    
    <activeProfiles>
        <activeProfile>gpg</activeProfile>
    </activeProfiles>
</settings>
```

**⚠️ 安全提示**: 将 `YOUR_GPG_KEY_ID_HERE` 替换为您的实际 GPG 密钥 ID，将 `YOUR_GPG_PASSPHRASE_HERE` 替换为您的 GPG 密码短语。在生产环境中考虑使用环境变量来存储密码短语。

### 第二步：安装 Unidbg

#### 克隆 Unidbg 仓库
```bash
# 选项 1: 从我的镜像克隆（推荐，访问不需要梯子）
git clone https://lostjay.xyz/gitea/github/unidbg.git

# 选项 2: 从 GitHub 克隆（备选）
git clone https://github.com/zhkl0228/unidbg.git
# 或
git clone git@github.com:zhkl0228/unidbg.git
```

#### 构建 Unidbg
```bash
cd unidbg
mvn clean install
cd ..
```

### 第三步：构建项目

#### 构建 Java 组件
```bash
# 构建所有 Maven 模块
mvn clean package -DskipTests
```

#### 安装 Python 包
```bash
# 以开发模式安装 Python 包
pip install -e .
```

## 🚀 使用方法

### 第一步：启动逆向服务器

逆向服务器提供签名生成的 HTTP 端点：

```bash
# 启动 Java 服务器
java -jar reverse_server/target/zhihu-android-reverse-server-1.0-SNAPSHOT.jar
```

服务器默认在 `http://localhost:17007` 启动。

### 第二步：运行测试

#### 运行所有测试
```bash
cd test
python -m unittest test_zhihu_android
```

#### 可用的测试方法
- `test_get_sign`: 测试签名生成端点
- `test_get_answers_from_author`: 测试从特定作者获取答案
- `test_search`: 测试搜索功能

### 第三步：使用 Python 客户端

```python
from zhihu_android.android import ZhihuAndroidCrawler

# 示例：搜索内容
async with ZhihuAndroidCrawler() as crawler:
    results = await crawler.search('芙宁娜')
    print(results)

# 示例：获取作者的答案
async with ZhihuAndroidCrawler() as crawler:
    async for answer in crawler.get_answers_from_author('author_id'):
        print(answer)
```

## 📁 项目结构

```
zhihu_android/
├── reverse_server/          # 用于签名生成的 Java 服务器
│   ├── src/                # Java 源代码
│   ├── target/             # 编译的 Java 构件
│   └── pom.xml            # Maven 配置
├── zhihu_android/          # Python 客户端库
│   ├── android.py         # 主要爬虫实现
│   ├── utils.py           # 工具函数
│   └── __init__.py        # 包初始化
├── test/                   # 测试套件
│   └── test_zhihu_android.py
├── crawler_utils/          # 附加工具
├── logs/                   # 应用日志
├── pom.xml                # 父级 Maven 配置
├── pyproject.toml         # Python 包配置
└── README.md              # 此文件
```

## 🔍 API 端点

### 签名生成
- **URL**: `POST /sign`
- **请求体**:
```json
{
    "path": "/api/v4/questions/423731362/answers",
    "authorization": "Bearer YOUR_TOKEN",
    "uuid": "YOUR_UUID",
    "appVersion": "1.0.0"
}
```

## 🧪 测试

### 运行特定测试
```bash
# 测试签名生成
python -m unittest test.test_zhihu_android.TestZhihuAndroid -k test_get_sign

# 测试搜索功能
python -m unittest test.test_zhihu_android.TestZhihuAndroid -k test_search

# 测试作者答案
python -m unittest test.test_zhihu_android.TestZhihuAndroid -k test_get_answers_from_author
```

### 运行所有测试
```bash
python -m unittest discover test
```

## 🔧 配置

### 环境变量
- `ZHIHU_SERVER_PORT`: 逆向服务器端口（默认: 17007）
- `ZHIHU_LOG_LEVEL`: 日志级别（默认: INFO）

### 服务器配置
可以通过修改 Java 应用属性或环境变量来配置逆向服务器。

## 📄 许可证

本项目采用 MIT 许可证 - 详情请参阅 LICENSE 文件。

## ⚠️ 免责声明

本项目仅供教育和研究目的使用。请尊重知乎的服务条款并负责任地使用此工具。作者不对此软件的误用负责。

## 📞 支持

如有问题和疑问：
- 在仓库中创建 issue
- 联系: lostjaychi@gmail.com

---

**注意**: 本项目需要正确设置 Unidbg 环境和 Maven 的 GPG 密钥。请仔细按照安装说明操作，确保所有组件正常工作。
