#!/bin/bash

echo "=== Java开发环境验证 ==="
echo

echo "1. Java版本信息："
java -version
echo

echo "2. Java编译器版本："
javac -version
echo

echo "3. JAVA_HOME环境变量："
echo "JAVA_HOME: $JAVA_HOME"
echo

echo "4. Maven版本信息："
mvn -version
echo

echo "5. Maven镜像源配置："
echo "配置文件位置: $HOME/.m2/settings.xml"
if [ -f "$HOME/.m2/settings.xml" ]; then
    echo "✓ Maven配置文件存在"
    echo "镜像源配置："
    grep -A 3 -B 1 "mirror" "$HOME/.m2/settings.xml" | grep -E "(id|name|url)" | head -9
else
    echo "✗ Maven配置文件不存在"
fi
echo

echo "6. 项目编译测试："
mvn clean compile -q
if [ $? -eq 0 ]; then
    echo "✓ 项目编译成功"
else
    echo "✗ 项目编译失败"
fi
echo

echo "7. 依赖解析测试："
mvn dependency:resolve -q
if [ $? -eq 0 ]; then
    echo "✓ 依赖解析成功"
else
    echo "✗ 依赖解析失败"
fi
echo

echo "=== 环境验证完成 ==="
echo "Java开发环境已成功配置！" 