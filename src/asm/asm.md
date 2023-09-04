# ir -> asm
指令  llc --march=riscv32 <src.ll>
## 转换方式概览
- 类定义 
  - 只用记录类内元素类型信息
  - 每个类的大小 元素的位置 每个变量占用空间均为4Bytes
- 全局变量
  - 汇编形式全局变量
  - 标签访问 标签可作指针
  - 转换字符串 换行符、反斜杠、双引号分别转义为 '\n'、'\\'、'\"'
- 函数转换
  - 局部变量的内存分配
  - 函数指令的转换
  - 函数调用约定
## RISC-V汇编
- `.section <section_name>`
  - `.test` 指令
  - `.bss` 以0初始化可读可写数据(全局数组)
  - `.data` 指定可读可写数据(指定初始值变量)
  - `.rodata` 只读数据(字符串自变量)
- `.globl <label>` 允许label在全局被访问(函数和变量)
- `.type <symbol_name>, <type_description>` 表明类型 可读性
- `<label>` 标签 表示内存地址
- `.word <number>` word大小数据
- `.zero <size>` 连续size个全为0字节
- `.size <symbol_name>, <size>` 符号所占字节数
- `.asciz` ASCII字符串(末尾自动加\0)
- 指令
## 函数转换
- 栈空间 sp


## 伪指令
Pseudo-instruction
- [ ] la rd, symbol  
- [ ] l{b, h, w} rd, symbol  
- [ ] s{b, h, w} rd, symbol, rt  
- [ ] nop  
- [ ] li  rd, imm  
- [ ] mv  rd, rs  
- [ ] not rd, rs  
- [ ] neg rd, rs  
- [ ] {seqz, snez, sltz, sgtz} rd, rs  
- [ ] {beqz, bnez, blez, bgez, bltz, btz} rs, offset  
- [ ] {bgt, ble, bgtu, bleu} rs, rt, offset  
- [ ] j   offset  
- [ ] jr  offset  
- [ ] ret  
- [ ] call offset  
- [ ] tail offset  