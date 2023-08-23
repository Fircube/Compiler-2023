# 目标代码生成 (LLVM IR)
## 1 概览
IR 是区分前后端的标志。  
语法检查、建构抽象语法树、生成 IR 都属于前端部分。  
通过 IR 生成目标代码、针对 IR 层面的优化都属于后端部分。  

clang 可以将代码编译到 LLVM IR，也可以将 LLVM 代码编译到目标平台。  
比如 clang main.c -S -emit-llvm --target=riscv32-unknown-elf -o main.ll 可以将 main.c 转换成 LLVM IR 并保存在 main.ll 中；  
而 clang -S main.ll --target=riscv32-unknown-elf -o main.s 可以将 main.ll 转换成汇编。  
llc 可以将 LLVM IR 转换成汇编，如 llc -march=riscv32 main.ll -o main.s 会将 main.ll 转换成汇编并存到 main.s。  

## 2 语法
### 2.1 基本语法
#### 2.1.1 类型 
- 整数
- 指针
- 数组 (`[<# elements> x <elementtype>]`)
- 结构类型
  - 普通类：`%<typename> = type { <type list> }`
  - 不能对齐：`%<typename> = type <{ <type list> }>`
#### 2.1.2 变量
非匿名(`[%@][-a-zA-Z$._][-a-zA-Z$._0-9]*`)  
匿名(`[%@](0|[1-9][0-9]*)`)
#### 2.1.3 常量
boolean、int、空指针常量null
#### 2.1.4 函数定义和声明
`define <ResultType> @<FunctionName>(<type> [name]...) { ... }`  
`declare <ResultType> @<FunctionName>(...)`
#### 2.1.5 标签
#### 2.1.6 指令
- 二元运算 add/sub/mul/sdiv/srem/shl/ashr/and/or/xor  
  `<result> = <operator> <type> <operand1>, <operand2>`  
- 控制流 br/ret  
  `br i1 <cond>, label <iftrue>, label <iffalse> ; Conditional branch`  
  `br label <dest> ; Unconditional branch`  
  `ret <type> <value> ; Return a value from a non-void function`  
  `ret void ; Return from void function`  
- 内存 alloca/load/store/getelementptr  
  `<result> = alloca <type>`  
  `<result> = load <ty>, ptr <pointer>`  
  `store <ty> <value>, ptr <pointer>`  
  `<result> = getelementptr <ty>, ptr <ptrval>{, <ty> <idx>}*`  
- 其他指令 icmp/call/phi  
  `<result> = icmp <cond> <ty> <op1>, <op2>`  
    eq/ne/ugt/uge/ult/ule/sgt/sge/slt/sle  
  `<result> = call <ResultType> @<FunctionName>(<arguments>)`  
  `call void @<FunctionName>(<arguments>)`  
  `<result> = phi <ty> [ <val0>, <label0>], ...`

## 2.2 从AST到IR
### 2.2.1 概览
全局：
- 全局变量(全局变量和字符串字面量(注意初始化过程))
- 全局函数(函数和成员方法)
- 类成员结构定义
### 2.2.2 命名
- 内部变量加(.1)后缀  
- 内建函数和成员方法(string.ord)  
- 函数不会冲突，类的方法(<ClassName>.<MethodName>),变量后面加上.<n>  
### 2.2.3 为全局变量进行初始化
直接/运行期_init
字符串字面量见后
### 2.2.3 为每个语法特性编写相应的转换逻辑
- 全局变量 初始化
- 函数处理
  - 函数变量处理(alloca)
  - 函数的参数(转换为IR类型)和函数名处理(不变)
  - 函数体的转换(语句块转换)。
- 类的处理：
  - 类的成员变量(利用结构类型把所有成员变量打包)
  - 类的方法（成员函数）(传入this)
  - 构造函数(变量初始化表达式计算出来赋值，this入参)
- 局部变量：store/load
- 处理语句：
  - 变量声明语句(alloca)
  - 条件语句
  - 循环语句(初始化部分（这部分可以不需要一个新基本块，沿用原基本块即可）、循环条件部分、循环体部分和更新 (step) 部分、循环结束部分)
  - 跳转语句
  - 表达式语句
    - new 表达式较为复杂
      - 多层数组 IR手动实现循环给数组初始化/在AST上实现循环
      - 类对象分配空间并调用构造函数
      - 如果当前层对应数组初始化为null
    - 逻辑表达式短路求值 分为两个基础块 br
- 内建功能
  - 内建函数及成员方法的实现
    - 用C语言编写内建函数和内建成员方法，用clang转换，并在用户输入的源代码对应的 IR 中声明对应的函数
  - string 的实现
  - 数组的实现
    - 记录数组本身和数组长度
    - 多种实现
      - 当成一个类，内部两个成员，一个指向数组的指针，一个数组长度
      - 当成指向数组的指针，数组长度放在

