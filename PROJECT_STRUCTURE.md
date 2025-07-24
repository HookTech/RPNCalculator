# RPN Calculator 项目结构

## 标准Maven项目目录结构

```
RPNCalculator/
├── src/
│   ├── main/
│   │   ├── java/                    # 主源代码目录
│   │   │   ├── component/           # 核心组件
│   │   │   │   ├── Interpreter.java
│   │   │   │   ├── Lexer.java
│   │   │   │   └── Parser.java
│   │   │   ├── element/            # 基础元素
│   │   │   │   ├── Factor.java
│   │   │   │   ├── Operator.java
│   │   │   │   ├── Token.java
│   │   │   │   └── UNDOAble.java
│   │   │   ├── errorflow/          # 异常处理
│   │   │   │   └── InsucientParametersException.java
│   │   │   ├── run/                # 程序入口
│   │   │   │   └── EntryClass.java
│   │   │   └── visitor/            # 访问者模式
│   │   │       ├── TokenElement.java
│   │   │       └── TokenElementVisitor.java
│   │   └── resources/              # 资源文件目录（可选）
│   └── test/
│       └── java/                   # 测试源代码目录
│           └── component/
│               ├── ParserTest.java
│               └── TestBase.java
├── target/                         # 编译输出目录（自动生成）
│   ├── classes/                    # 编译后的主类文件
│   ├── test-classes/               # 编译后的测试类文件
│   └── ...
├── pom.xml                         # Maven项目配置文件
├── .gitignore                      # Git忽略文件配置
└── README.md                       # 项目说明文档
```

## 重要说明

### ✅ 正确的做法：
- 源代码文件（`.java`）放在 `src/main/java/` 目录下
- 测试文件放在 `src/test/java/` 目录下
- 编译后的文件（`.class`）自动生成在 `target/` 目录下
- 使用 `mvn clean compile` 编译项目
- 使用 `mvn clean test` 运行测试

### ❌ 错误的做法：
- 将编译后的 `.class` 文件提交到版本控制
- 在 `src` 目录下直接放置 `.class` 文件
- 手动编译而不使用Maven

## 编译和运行

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包项目
mvn package

# 运行主程序
java -cp target/classes run.EntryClass
```

## IDE配置

如果您使用IDE（如IntelliJ IDEA、Eclipse、VS Code），请确保：
1. 将项目作为Maven项目导入
2. 源代码目录设置为 `src/main/java`
3. 测试目录设置为 `src/test/java`
4. 输出目录设置为 `target/classes`

这样可以确保IDE正确识别项目结构，支持代码跳转和索引功能。 