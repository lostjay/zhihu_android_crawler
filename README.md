# Zhihu Android Reverse Engineering Solution

A sophisticated reverse engineering solution for Zhihu Android App that calculates the `x-zse-96` parameters required for API authentication. This project provides a complete implementation for bypassing Zhihu's signature verification system.

## 🌐 Official Links

- **Official Website**: [https://lostjay.xyz/gitea/github/zhihu_android_crawler](https://lostjay.xyz/gitea/github/zhihu_android_crawler)
- **GitHub Mirror**: [https://github.com/lostjay/zhihu_android_crawler](https://github.com/lostjay/zhihu_android_crawler)

[中文文档](README_CN.md) | [English](README.md)

## 🚀 Features

- **Signature Generation**: Calculates `x-zse-96` parameters for Zhihu API requests
- **Unidbg Integration**: Uses Unidbg to emulate Android native libraries
- **RESTful API Server**: Provides HTTP endpoints for signature generation
- **Python Client**: Easy-to-use Python wrapper for Zhihu API interactions
- **Comprehensive Testing**: Unit tests for all major functionality

## 📋 Prerequisites

### System Requirements
- **Java 11+** (for Unidbg and Maven)
- **Python 3.10+** (for the Python client)
- **Maven 3.6+** (for building Java components)
- **Git** (for cloning repositories)

## 🔧 Installation

### Step 1: GPG Key Setup (Required for Maven)

#### Check Existing GPG Keys
```bash
gpg --list-secret-keys
```

#### Generate New GPG Key (if needed)
```bash
# Generate a new GPG key pair
gpg --gen-key

# Follow the prompts:
# 1. Choose "RSA and RSA" (default)
# 2. Choose 4096 bits for maximum security
# 3. Choose 0 (does not expire)
# 4. Enter your name and email
# 5. Enter a passphrase (recommended)
```

#### Configure Maven with GPG Key

1. **Export your GPG key ID**:
```bash
gpg --list-secret-keys --keyid-format LONG
# Look for a line like: sec   rsa4096/ABC123DEF4567890 2023-01-01 [SC]
# The key ID is: ABC123DEF4567890
```

2. **Create Maven settings.xml** (if it doesn't exist):
```bash
mkdir -p ~/.m2
touch ~/.m2/settings.xml
```

3. **Add GPG configuration to settings.xml**:
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

**⚠️ Security Note**: Replace `YOUR_GPG_KEY_ID_HERE` with your actual GPG key ID and `YOUR_GPG_PASSPHRASE_HERE` with your GPG passphrase. Consider using environment variables for the passphrase in production environments.

### Step 2: Install Unidbg

#### Clone Unidbg Repository
```bash
# Option 1: Clone from my mirror (recommended for no vpn needed)
git clone https://lostjay.xyz/gitea/github/unidbg.git

# Option 2: Clone from GitHub (alternative)
git clone https://github.com/zhkl0228/unidbg.git
# or
git clone git@github.com:zhkl0228/unidbg.git
```

#### Build Unidbg
```bash
cd unidbg
mvn clean install
cd ..
```

### Step 3: Build the Project

#### Build Java Components
```bash
# Build all Maven modules
mvn clean package -DskipTests
```

#### Install Python Package
```bash
# Install the Python package in development mode
pip install -e .
```

## 🚀 Usage

### Step 1: Start the Reverse Server

The reverse server provides HTTP endpoints for signature generation:

```bash
# Start the Java server
java -jar reverse_server/target/zhihu-android-reverse-server-1.0-SNAPSHOT.jar
```

The server will start on `http://localhost:17007` by default.

### Step 2: Run Tests

#### Run All Tests
```bash
cd test
python -m unittest test_zhihu_android
```

#### Available Test Methods
- `test_get_sign`: Tests signature generation endpoint
- `test_get_answers_from_author`: Tests fetching answers from specific authors
- `test_search`: Tests search functionality

### Step 3: Use the Python Client

```python
from zhihu_android.android import ZhihuAndroidCrawler

# Example: Search for content
async with ZhihuAndroidCrawler() as crawler:
    results = await crawler.search('芙宁娜')
    print(results)

# Example: Get answers from an author
async with ZhihuAndroidCrawler() as crawler:
    async for answer in crawler.get_answers_from_author('author_id'):
        print(answer)
```

## 📁 Project Structure

```
zhihu_android/
├── reverse_server/          # Java server for signature generation
│   ├── src/                # Java source code
│   ├── target/             # Compiled Java artifacts
│   └── pom.xml            # Maven configuration
├── zhihu_android/          # Python client library
│   ├── android.py         # Main crawler implementation
│   ├── utils.py           # Utility functions
│   └── __init__.py        # Package initialization
├── test/                   # Test suite
│   └── test_zhihu_android.py
├── crawler_utils/          # Additional utilities
├── logs/                   # Application logs
├── pom.xml                # Parent Maven configuration
├── pyproject.toml         # Python package configuration
└── README.md              # This file
```

## 🔍 API Endpoints

### Signature Generation
- **URL**: `POST /sign`
- **Body**:
```json
{
    "path": "/api/v4/questions/423731362/answers",
    "authorization": "Bearer YOUR_TOKEN",
    "uuid": "YOUR_UUID",
    "appVersion": "1.0.0"
}
```

## 🧪 Testing

### Run Specific Tests
```bash
# Test signature generation
python -m unittest test.test_zhihu_android.TestZhihuAndroid -k test_get_sign

# Test search functionality
python -m unittest test.test_zhihu_android.TestZhihuAndroid -k test_search

# Test author answers
python -m unittest test.test_zhihu_android.TestZhihuAndroid -k test_get_answers_from_author
```

### Run All Tests
```bash
python -m unittest discover test
```

## 🔧 Configuration

### Environment Variables
- `ZHIHU_SERVER_PORT`: Port for the reverse server (default: 17007)
- `ZHIHU_LOG_LEVEL`: Logging level (default: INFO)

### Server Configuration
The reverse server can be configured by modifying the Java application properties or environment variables.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## ⚠️ Disclaimer

This project is for educational and research purposes only. Please respect Zhihu's Terms of Service and use this tool responsibly. The authors are not responsible for any misuse of this software.

## 📞 Support

For issues and questions:
- Create an issue on the repository
- Contact: lostjaychi@gmail.com

---

**Note**: This project requires proper setup of the Unidbg environment and GPG keys for Maven. Follow the installation instructions carefully to ensure all components work correctly.

## Buy Me a Milk Tea

If you find this project helpful, feel free to buy the author a milk tea via WeChat Pay!

![WeChat Pay QR Code](https://lostjay.xyz/wechatpay)