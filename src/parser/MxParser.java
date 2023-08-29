// Generated from C:/Users/ymy929/IdeaProjects/Mx-Compiler/src/parser\MxParser.g4 by ANTLR 4.12.0
package src.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MxParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    public static final int Add = 1, Sub = 2, Mul = 3, Div = 4, Mod = 5, Greater = 6, Less = 7, GreaterEqual = 8, LessEqual = 9, NotEqual = 10, Equal = 11, And = 12, Or = 13, Not = 14, RightShift = 15, LeftShift = 16, BitAnd = 17, BitOr = 18, BitXor = 19, BitNot = 20, Assign = 21, Increase = 22, Decrease = 23, Dot = 24, Question = 25, Colon = 26, Semi = 27, Comma = 28, Quite = 29, LeftParen = 30, RightParen = 31, LeftBracket = 32, RightBracket = 33, LeftBrace = 34, RightBrace = 35, Void = 36, Bool = 37, Int = 38, String = 39, New = 40, Class = 41, Null = 42, True = 43, False = 44, This = 45, If = 46, Else = 47, For = 48, While = 49, Break = 50, Continue = 51, Return = 52, Identifier = 53, ConstantInt = 54, ConstantStr = 55, Whitespace = 56, Newline = 57, BlockComment = 58, LineComment = 59;
    public static final int RULE_program = 0, RULE_funcDef = 1, RULE_parameter = 2, RULE_unitParameter = 3, RULE_classDef = 4, RULE_classConstructor = 5, RULE_varDef = 6, RULE_unitVarDef = 7, RULE_returnType = 8, RULE_defType = 9, RULE_baseType = 10, RULE_suite = 11, RULE_realParameter = 12, RULE_statement = 13, RULE_expression = 14, RULE_primary = 15;

    private static String[] makeRuleNames() {
        return new String[]{"program", "funcDef", "parameter", "unitParameter", "classDef", "classConstructor", "varDef", "unitVarDef", "returnType", "defType", "baseType", "suite", "realParameter", "statement", "expression", "primary"};
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'<'", "'>='", "'<='", "'!='", "'=='", "'&&'", "'||'", "'!'", "'>>'", "'<<'", "'&'", "'|'", "'^'", "'~'", "'='", "'++'", "'--'", "'.'", "'?'", "':'", "';'", "','", "'\"'", "'('", "')'", "'['", "']'", "'{'", "'}'", "'void'", "'bool'", "'int'", "'string'", "'new'", "'class'", "'null'", "'true'", "'false'", "'this'", "'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", "'return'"};
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{null, "Add", "Sub", "Mul", "Div", "Mod", "Greater", "Less", "GreaterEqual", "LessEqual", "NotEqual", "Equal", "And", "Or", "Not", "RightShift", "LeftShift", "BitAnd", "BitOr", "BitXor", "BitNot", "Assign", "Increase", "Decrease", "Dot", "Question", "Colon", "Semi", "Comma", "Quite", "LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", "RightBrace", "Void", "Bool", "Int", "String", "New", "Class", "Null", "True", "False", "This", "If", "Else", "For", "While", "Break", "Continue", "Return", "Identifier", "ConstantInt", "ConstantStr", "Whitespace", "Newline", "BlockComment", "LineComment"};
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "MxParser.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public MxParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ProgramContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(MxParser.EOF, 0);
        }

        public List<FuncDefContext> funcDef() {
            return getRuleContexts(FuncDefContext.class);
        }

        public FuncDefContext funcDef(int i) {
            return getRuleContext(FuncDefContext.class, i);
        }

        public List<ClassDefContext> classDef() {
            return getRuleContexts(ClassDefContext.class);
        }

        public ClassDefContext classDef(int i) {
            return getRuleContext(ClassDefContext.class, i);
        }

        public List<VarDefContext> varDef() {
            return getRuleContexts(VarDefContext.class);
        }

        public VarDefContext varDef(int i) {
            return getRuleContext(VarDefContext.class, i);
        }

        public ProgramContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_program;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterProgram(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitProgram(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitProgram(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ProgramContext program() throws RecognitionException {
        ProgramContext _localctx = new ProgramContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_program);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(37);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9010429070147584L) != 0)) {
                    {
                        setState(35);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                            case 1: {
                                setState(32);
                                funcDef();
                            }
                            break;
                            case 2: {
                                setState(33);
                                classDef();
                            }
                            break;
                            case 3: {
                                setState(34);
                                varDef();
                            }
                            break;
                        }
                    }
                    setState(39);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(40);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class FuncDefContext extends ParserRuleContext {
        public ReturnTypeContext returnType() {
            return getRuleContext(ReturnTypeContext.class, 0);
        }

        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public SuiteContext suite() {
            return getRuleContext(SuiteContext.class, 0);
        }

        public ParameterContext parameter() {
            return getRuleContext(ParameterContext.class, 0);
        }

        public FuncDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_funcDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterFuncDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitFuncDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitFuncDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FuncDefContext funcDef() throws RecognitionException {
        FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_funcDef);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(42);
                returnType();
                setState(43);
                match(Identifier);
                setState(44);
                match(LeftParen);
                setState(46);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9008161327415296L) != 0)) {
                    {
                        setState(45);
                        parameter();
                    }
                }

                setState(48);
                match(RightParen);
                setState(49);
                suite();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ParameterContext extends ParserRuleContext {
        public List<UnitParameterContext> unitParameter() {
            return getRuleContexts(UnitParameterContext.class);
        }

        public UnitParameterContext unitParameter(int i) {
            return getRuleContext(UnitParameterContext.class, i);
        }

        public List<TerminalNode> Comma() {
            return getTokens(MxParser.Comma);
        }

        public TerminalNode Comma(int i) {
            return getToken(MxParser.Comma, i);
        }

        public ParameterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_parameter;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterParameter(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitParameter(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitParameter(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ParameterContext parameter() throws RecognitionException {
        ParameterContext _localctx = new ParameterContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_parameter);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(51);
                unitParameter();
                setState(56);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == Comma) {
                    {
                        {
                            setState(52);
                            match(Comma);
                            setState(53);
                            unitParameter();
                        }
                    }
                    setState(58);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class UnitParameterContext extends ParserRuleContext {
        public DefTypeContext defType() {
            return getRuleContext(DefTypeContext.class, 0);
        }

        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public UnitParameterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_unitParameter;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterUnitParameter(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitUnitParameter(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitUnitParameter(this);
            else return visitor.visitChildren(this);
        }
    }

    public final UnitParameterContext unitParameter() throws RecognitionException {
        UnitParameterContext _localctx = new UnitParameterContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_unitParameter);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(59);
                defType();
                setState(60);
                match(Identifier);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ClassDefContext extends ParserRuleContext {
        public TerminalNode Class() {
            return getToken(MxParser.Class, 0);
        }

        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public TerminalNode LeftBrace() {
            return getToken(MxParser.LeftBrace, 0);
        }

        public TerminalNode RightBrace() {
            return getToken(MxParser.RightBrace, 0);
        }

        public TerminalNode Semi() {
            return getToken(MxParser.Semi, 0);
        }

        public List<VarDefContext> varDef() {
            return getRuleContexts(VarDefContext.class);
        }

        public VarDefContext varDef(int i) {
            return getRuleContext(VarDefContext.class, i);
        }

        public List<ClassConstructorContext> classConstructor() {
            return getRuleContexts(ClassConstructorContext.class);
        }

        public ClassConstructorContext classConstructor(int i) {
            return getRuleContext(ClassConstructorContext.class, i);
        }

        public List<FuncDefContext> funcDef() {
            return getRuleContexts(FuncDefContext.class);
        }

        public FuncDefContext funcDef(int i) {
            return getRuleContext(FuncDefContext.class, i);
        }

        public ClassDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_classDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterClassDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitClassDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitClassDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ClassDefContext classDef() throws RecognitionException {
        ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_classDef);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(62);
                match(Class);
                setState(63);
                match(Identifier);
                setState(64);
                match(LeftBrace);
                setState(70);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9008230046892032L) != 0)) {
                    {
                        setState(68);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
                            case 1: {
                                setState(65);
                                varDef();
                            }
                            break;
                            case 2: {
                                setState(66);
                                classConstructor();
                            }
                            break;
                            case 3: {
                                setState(67);
                                funcDef();
                            }
                            break;
                        }
                    }
                    setState(72);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(73);
                match(RightBrace);
                setState(74);
                match(Semi);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ClassConstructorContext extends ParserRuleContext {
        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public SuiteContext suite() {
            return getRuleContext(SuiteContext.class, 0);
        }

        public ClassConstructorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_classConstructor;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterClassConstructor(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitClassConstructor(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitClassConstructor(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ClassConstructorContext classConstructor() throws RecognitionException {
        ClassConstructorContext _localctx = new ClassConstructorContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_classConstructor);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(76);
                match(Identifier);
                setState(77);
                match(LeftParen);
                setState(78);
                match(RightParen);
                setState(79);
                suite();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class VarDefContext extends ParserRuleContext {
        public DefTypeContext defType() {
            return getRuleContext(DefTypeContext.class, 0);
        }

        public List<UnitVarDefContext> unitVarDef() {
            return getRuleContexts(UnitVarDefContext.class);
        }

        public UnitVarDefContext unitVarDef(int i) {
            return getRuleContext(UnitVarDefContext.class, i);
        }

        public TerminalNode Semi() {
            return getToken(MxParser.Semi, 0);
        }

        public List<TerminalNode> Comma() {
            return getTokens(MxParser.Comma);
        }

        public TerminalNode Comma(int i) {
            return getToken(MxParser.Comma, i);
        }

        public VarDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_varDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterVarDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitVarDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitVarDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final VarDefContext varDef() throws RecognitionException {
        VarDefContext _localctx = new VarDefContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_varDef);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(81);
                defType();
                setState(82);
                unitVarDef();
                setState(87);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == Comma) {
                    {
                        {
                            setState(83);
                            match(Comma);
                            setState(84);
                            unitVarDef();
                        }
                    }
                    setState(89);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(90);
                match(Semi);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class UnitVarDefContext extends ParserRuleContext {
        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public TerminalNode Assign() {
            return getToken(MxParser.Assign, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public UnitVarDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_unitVarDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterUnitVarDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitUnitVarDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitUnitVarDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final UnitVarDefContext unitVarDef() throws RecognitionException {
        UnitVarDefContext _localctx = new UnitVarDefContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_unitVarDef);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(92);
                match(Identifier);
                setState(95);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == Assign) {
                    {
                        setState(93);
                        match(Assign);
                        setState(94);
                        expression(0);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ReturnTypeContext extends ParserRuleContext {
        public TerminalNode Void() {
            return getToken(MxParser.Void, 0);
        }

        public DefTypeContext defType() {
            return getRuleContext(DefTypeContext.class, 0);
        }

        public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_returnType;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterReturnType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitReturnType(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitReturnType(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ReturnTypeContext returnType() throws RecognitionException {
        ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_returnType);
        try {
            setState(99);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case Void:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(97);
                    match(Void);
                }
                break;
                case Bool:
                case Int:
                case String:
                case Identifier:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(98);
                    defType();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class DefTypeContext extends ParserRuleContext {
        public BaseTypeContext baseType() {
            return getRuleContext(BaseTypeContext.class, 0);
        }

        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public List<TerminalNode> LeftBracket() {
            return getTokens(MxParser.LeftBracket);
        }

        public TerminalNode LeftBracket(int i) {
            return getToken(MxParser.LeftBracket, i);
        }

        public List<TerminalNode> RightBracket() {
            return getTokens(MxParser.RightBracket);
        }

        public TerminalNode RightBracket(int i) {
            return getToken(MxParser.RightBracket, i);
        }

        public DefTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_defType;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterDefType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitDefType(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitDefType(this);
            else return visitor.visitChildren(this);
        }
    }

    public final DefTypeContext defType() throws RecognitionException {
        DefTypeContext _localctx = new DefTypeContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_defType);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(103);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case Bool:
                    case Int:
                    case String: {
                        setState(101);
                        baseType();
                    }
                    break;
                    case Identifier: {
                        setState(102);
                        match(Identifier);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(109);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == LeftBracket) {
                    {
                        {
                            setState(105);
                            match(LeftBracket);
                            setState(106);
                            match(RightBracket);
                        }
                    }
                    setState(111);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BaseTypeContext extends ParserRuleContext {
        public TerminalNode Bool() {
            return getToken(MxParser.Bool, 0);
        }

        public TerminalNode Int() {
            return getToken(MxParser.Int, 0);
        }

        public TerminalNode String() {
            return getToken(MxParser.String, 0);
        }

        public BaseTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_baseType;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterBaseType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitBaseType(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitBaseType(this);
            else return visitor.visitChildren(this);
        }
    }

    public final BaseTypeContext baseType() throws RecognitionException {
        BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_baseType);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(112);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class SuiteContext extends ParserRuleContext {
        public TerminalNode LeftBrace() {
            return getToken(MxParser.LeftBrace, 0);
        }

        public TerminalNode RightBrace() {
            return getToken(MxParser.RightBrace, 0);
        }

        public List<StatementContext> statement() {
            return getRuleContexts(StatementContext.class);
        }

        public StatementContext statement(int i) {
            return getRuleContext(StatementContext.class, i);
        }

        public SuiteContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_suite;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterSuite(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitSuite(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitSuite(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuiteContext suite() throws RecognitionException {
        SuiteContext _localctx = new SuiteContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_suite);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(114);
                match(LeftBrace);
                setState(118);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 71914538488840198L) != 0)) {
                    {
                        {
                            setState(115);
                            statement();
                        }
                    }
                    setState(120);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(121);
                match(RightBrace);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RealParameterContext extends ParserRuleContext {
        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public List<TerminalNode> Comma() {
            return getTokens(MxParser.Comma);
        }

        public TerminalNode Comma(int i) {
            return getToken(MxParser.Comma, i);
        }

        public RealParameterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_realParameter;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterRealParameter(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitRealParameter(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitRealParameter(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RealParameterContext realParameter() throws RecognitionException {
        RealParameterContext _localctx = new RealParameterContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_realParameter);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(123);
                expression(0);
                setState(128);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == Comma) {
                    {
                        {
                            setState(124);
                            match(Comma);
                            setState(125);
                            expression(0);
                        }
                    }
                    setState(130);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StatementContext extends ParserRuleContext {
        public StatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_statement;
        }

        public StatementContext() {
        }

        public void copyFrom(StatementContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class VarDefStmtContext extends StatementContext {
        public VarDefContext varDef() {
            return getRuleContext(VarDefContext.class, 0);
        }

        public VarDefStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterVarDefStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitVarDefStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitVarDefStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ForStmtContext extends StatementContext {
        public VarDefContext varInit;
        public ExpressionContext forInit;
        public ExpressionContext forCon;
        public ExpressionContext forStep;

        public TerminalNode For() {
            return getToken(MxParser.For, 0);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public List<TerminalNode> Semi() {
            return getTokens(MxParser.Semi);
        }

        public TerminalNode Semi(int i) {
            return getToken(MxParser.Semi, i);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public StatementContext statement() {
            return getRuleContext(StatementContext.class, 0);
        }

        public VarDefContext varDef() {
            return getRuleContext(VarDefContext.class, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public ForStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterForStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitForStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitForStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExprStmtContext extends StatementContext {
        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode Semi() {
            return getToken(MxParser.Semi, 0);
        }

        public ExprStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterExprStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitExprStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitExprStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WhileStmtContext extends StatementContext {
        public TerminalNode While() {
            return getToken(MxParser.While, 0);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public StatementContext statement() {
            return getRuleContext(StatementContext.class, 0);
        }

        public WhileStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterWhileStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitWhileStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitWhileStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IfStmtContext extends StatementContext {
        public StatementContext trueStmt;
        public StatementContext falseStmt;

        public TerminalNode If() {
            return getToken(MxParser.If, 0);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public List<StatementContext> statement() {
            return getRuleContexts(StatementContext.class);
        }

        public StatementContext statement(int i) {
            return getRuleContext(StatementContext.class, i);
        }

        public TerminalNode Else() {
            return getToken(MxParser.Else, 0);
        }

        public IfStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterIfStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitIfStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitIfStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BreakStmtContext extends StatementContext {
        public TerminalNode Break() {
            return getToken(MxParser.Break, 0);
        }

        public TerminalNode Semi() {
            return getToken(MxParser.Semi, 0);
        }

        public BreakStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterBreakStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitBreakStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitBreakStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class EmptyStmtContext extends StatementContext {
        public TerminalNode Semi() {
            return getToken(MxParser.Semi, 0);
        }

        public EmptyStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterEmptyStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitEmptyStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitEmptyStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BlockContext extends StatementContext {
        public SuiteContext suite() {
            return getRuleContext(SuiteContext.class, 0);
        }

        public BlockContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterBlock(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitBlock(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitBlock(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ReturnStmtContext extends StatementContext {
        public TerminalNode Return() {
            return getToken(MxParser.Return, 0);
        }

        public TerminalNode Semi() {
            return getToken(MxParser.Semi, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public ReturnStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterReturnStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitReturnStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitReturnStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ContinueStmtContext extends StatementContext {
        public TerminalNode Continue() {
            return getToken(MxParser.Continue, 0);
        }

        public TerminalNode Semi() {
            return getToken(MxParser.Semi, 0);
        }

        public ContinueStmtContext(StatementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterContinueStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitContinueStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitContinueStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final StatementContext statement() throws RecognitionException {
        StatementContext _localctx = new StatementContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_statement);
        int _la;
        try {
            setState(179);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
                case 1:
                    _localctx = new BlockContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(131);
                    suite();
                }
                break;
                case 2:
                    _localctx = new VarDefStmtContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(132);
                    varDef();
                }
                break;
                case 3:
                    _localctx = new IfStmtContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(133);
                    match(If);
                    setState(134);
                    match(LeftParen);
                    setState(135);
                    expression(0);
                    setState(136);
                    match(RightParen);
                    setState(137);
                    ((IfStmtContext) _localctx).trueStmt = statement();
                    setState(140);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                        case 1: {
                            setState(138);
                            match(Else);
                            setState(139);
                            ((IfStmtContext) _localctx).falseStmt = statement();
                        }
                        break;
                    }
                }
                break;
                case 4:
                    _localctx = new WhileStmtContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(142);
                    match(While);
                    setState(143);
                    match(LeftParen);
                    setState(144);
                    expression(0);
                    setState(145);
                    match(RightParen);
                    setState(146);
                    statement();
                }
                break;
                case 5:
                    _localctx = new ForStmtContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                {
                    setState(148);
                    match(For);
                    setState(149);
                    match(LeftParen);
                    setState(155);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                        case 1: {
                            setState(150);
                            ((ForStmtContext) _localctx).varInit = varDef();
                        }
                        break;
                        case 2: {
                            {
                                setState(152);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 63117466079870982L) != 0)) {
                                    {
                                        setState(151);
                                        ((ForStmtContext) _localctx).forInit = expression(0);
                                    }
                                }

                                setState(154);
                                match(Semi);
                            }
                        }
                        break;
                    }
                    setState(158);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 63117466079870982L) != 0)) {
                        {
                            setState(157);
                            ((ForStmtContext) _localctx).forCon = expression(0);
                        }
                    }

                    setState(160);
                    match(Semi);
                    setState(162);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 63117466079870982L) != 0)) {
                        {
                            setState(161);
                            ((ForStmtContext) _localctx).forStep = expression(0);
                        }
                    }

                    setState(164);
                    match(RightParen);
                    setState(165);
                    statement();
                }
                break;
                case 6:
                    _localctx = new ReturnStmtContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                {
                    setState(166);
                    match(Return);
                    setState(168);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 63117466079870982L) != 0)) {
                        {
                            setState(167);
                            expression(0);
                        }
                    }

                    setState(170);
                    match(Semi);
                }
                break;
                case 7:
                    _localctx = new BreakStmtContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                {
                    setState(171);
                    match(Break);
                    setState(172);
                    match(Semi);
                }
                break;
                case 8:
                    _localctx = new ContinueStmtContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                {
                    setState(173);
                    match(Continue);
                    setState(174);
                    match(Semi);
                }
                break;
                case 9:
                    _localctx = new ExprStmtContext(_localctx);
                    enterOuterAlt(_localctx, 9);
                {
                    setState(175);
                    expression(0);
                    setState(176);
                    match(Semi);
                }
                break;
                case 10:
                    _localctx = new EmptyStmtContext(_localctx);
                    enterOuterAlt(_localctx, 10);
                {
                    setState(178);
                    match(Semi);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExpressionContext extends ParserRuleContext {
        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }

        public ExpressionContext() {
        }

        public void copyFrom(ExpressionContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NewExprContext extends ExpressionContext {
        public TerminalNode New() {
            return getToken(MxParser.New, 0);
        }

        public BaseTypeContext baseType() {
            return getRuleContext(BaseTypeContext.class, 0);
        }

        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public List<TerminalNode> LeftBracket() {
            return getTokens(MxParser.LeftBracket);
        }

        public TerminalNode LeftBracket(int i) {
            return getToken(MxParser.LeftBracket, i);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public List<TerminalNode> RightBracket() {
            return getTokens(MxParser.RightBracket);
        }

        public TerminalNode RightBracket(int i) {
            return getToken(MxParser.RightBracket, i);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public NewExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterNewExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitNewExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitNewExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class UnaryExprContext extends ExpressionContext {
        public Token prefix;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode Not() {
            return getToken(MxParser.Not, 0);
        }

        public TerminalNode BitNot() {
            return getToken(MxParser.BitNot, 0);
        }

        public TerminalNode Add() {
            return getToken(MxParser.Add, 0);
        }

        public TerminalNode Sub() {
            return getToken(MxParser.Sub, 0);
        }

        public UnaryExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterUnaryExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitUnaryExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitUnaryExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class TernaryExprContext extends ExpressionContext {
        public Token op;

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode Colon() {
            return getToken(MxParser.Colon, 0);
        }

        public TerminalNode Question() {
            return getToken(MxParser.Question, 0);
        }

        public TernaryExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterTernaryExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitTernaryExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitTernaryExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ArrayExprContext extends ExpressionContext {
        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode LeftBracket() {
            return getToken(MxParser.LeftBracket, 0);
        }

        public TerminalNode RightBracket() {
            return getToken(MxParser.RightBracket, 0);
        }

        public ArrayExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterArrayExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitArrayExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitArrayExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class MemberExprContext extends ExpressionContext {
        public Token op;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public TerminalNode Dot() {
            return getToken(MxParser.Dot, 0);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public RealParameterContext realParameter() {
            return getRuleContext(RealParameterContext.class, 0);
        }

        public MemberExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterMemberExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitMemberExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitMemberExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AtomExprContext extends ExpressionContext {
        public PrimaryContext primary() {
            return getRuleContext(PrimaryContext.class, 0);
        }

        public AtomExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterAtomExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitAtomExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitAtomExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BinaryExprContext extends ExpressionContext {
        public Token op;

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode Mul() {
            return getToken(MxParser.Mul, 0);
        }

        public TerminalNode Div() {
            return getToken(MxParser.Div, 0);
        }

        public TerminalNode Mod() {
            return getToken(MxParser.Mod, 0);
        }

        public TerminalNode Add() {
            return getToken(MxParser.Add, 0);
        }

        public TerminalNode Sub() {
            return getToken(MxParser.Sub, 0);
        }

        public TerminalNode RightShift() {
            return getToken(MxParser.RightShift, 0);
        }

        public TerminalNode LeftShift() {
            return getToken(MxParser.LeftShift, 0);
        }

        public TerminalNode Greater() {
            return getToken(MxParser.Greater, 0);
        }

        public TerminalNode Less() {
            return getToken(MxParser.Less, 0);
        }

        public TerminalNode GreaterEqual() {
            return getToken(MxParser.GreaterEqual, 0);
        }

        public TerminalNode LessEqual() {
            return getToken(MxParser.LessEqual, 0);
        }

        public TerminalNode Equal() {
            return getToken(MxParser.Equal, 0);
        }

        public TerminalNode NotEqual() {
            return getToken(MxParser.NotEqual, 0);
        }

        public TerminalNode BitAnd() {
            return getToken(MxParser.BitAnd, 0);
        }

        public TerminalNode BitXor() {
            return getToken(MxParser.BitXor, 0);
        }

        public TerminalNode BitOr() {
            return getToken(MxParser.BitOr, 0);
        }

        public TerminalNode And() {
            return getToken(MxParser.And, 0);
        }

        public TerminalNode Or() {
            return getToken(MxParser.Or, 0);
        }

        public BinaryExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterBinaryExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitBinaryExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitBinaryExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class PreExprContext extends ExpressionContext {
        public Token prefix;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode Increase() {
            return getToken(MxParser.Increase, 0);
        }

        public TerminalNode Decrease() {
            return getToken(MxParser.Decrease, 0);
        }

        public PreExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterPreExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitPreExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitPreExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class FuncCallExprContext extends ExpressionContext {
        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public RealParameterContext realParameter() {
            return getRuleContext(RealParameterContext.class, 0);
        }

        public FuncCallExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterFuncCallExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitFuncCallExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitFuncCallExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignExprContext extends ExpressionContext {
        public Token op;

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode Assign() {
            return getToken(MxParser.Assign, 0);
        }

        public AssignExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterAssignExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitAssignExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor)
                return ((MxParserVisitor<? extends T>) visitor).visitAssignExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class PostExprContext extends ExpressionContext {
        public Token postfix;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode Increase() {
            return getToken(MxParser.Increase, 0);
        }

        public TerminalNode Decrease() {
            return getToken(MxParser.Decrease, 0);
        }

        public PostExprContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterPostExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitPostExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitPostExpr(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ExpressionContext expression() throws RecognitionException {
        return expression(0);
    }

    private ExpressionContext expression(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
        ExpressionContext _prevctx = _localctx;
        int _startState = 28;
        enterRecursionRule(_localctx, 28, RULE_expression, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(213);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LeftParen:
                    case Null:
                    case True:
                    case False:
                    case This:
                    case Identifier:
                    case ConstantInt:
                    case ConstantStr: {
                        _localctx = new AtomExprContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(182);
                        primary();
                    }
                    break;
                    case Increase:
                    case Decrease: {
                        _localctx = new PreExprContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(183);
                        ((PreExprContext) _localctx).prefix = _input.LT(1);
                        _la = _input.LA(1);
                        if (!(_la == Increase || _la == Decrease)) {
                            ((PreExprContext) _localctx).prefix = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(184);
                        expression(15);
                    }
                    break;
                    case Add:
                    case Sub:
                    case Not:
                    case BitNot: {
                        _localctx = new UnaryExprContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(185);
                        ((UnaryExprContext) _localctx).prefix = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 1064966L) != 0))) {
                            ((UnaryExprContext) _localctx).prefix = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(186);
                        expression(14);
                    }
                    break;
                    case New: {
                        _localctx = new NewExprContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(187);
                        match(New);
                        setState(190);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case Bool:
                            case Int:
                            case String: {
                                setState(188);
                                baseType();
                            }
                            break;
                            case Identifier: {
                                setState(189);
                                match(Identifier);
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                        setState(211);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 24, _ctx)) {
                            case 1: {
                                setState(196);
                                _errHandler.sync(this);
                                _alt = 1;
                                do {
                                    switch (_alt) {
                                        case 1: {
                                            {
                                                setState(192);
                                                match(LeftBracket);
                                                setState(193);
                                                expression(0);
                                                setState(194);
                                                match(RightBracket);
                                            }
                                        }
                                        break;
                                        default:
                                            throw new NoViableAltException(this);
                                    }
                                    setState(198);
                                    _errHandler.sync(this);
                                    _alt = getInterpreter().adaptivePredict(_input, 21, _ctx);
                                } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                                setState(204);
                                _errHandler.sync(this);
                                _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
                                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                                    if (_alt == 1) {
                                        {
                                            {
                                                setState(200);
                                                match(LeftBracket);
                                                setState(201);
                                                match(RightBracket);
                                            }
                                        }
                                    }
                                    setState(206);
                                    _errHandler.sync(this);
                                    _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
                                }
                            }
                            break;
                            case 2: {
                                setState(209);
                                _errHandler.sync(this);
                                switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
                                    case 1: {
                                        setState(207);
                                        match(LeftParen);
                                        setState(208);
                                        match(RightParen);
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(279);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 30, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(277);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
                                case 1: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(215);
                                    if (!(precpred(_ctx, 12)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 12)");
                                    setState(216);
                                    ((BinaryExprContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0))) {
                                        ((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(217);
                                    expression(13);
                                }
                                break;
                                case 2: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(218);
                                    if (!(precpred(_ctx, 11)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 11)");
                                    setState(219);
                                    ((BinaryExprContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == Add || _la == Sub)) {
                                        ((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(220);
                                    expression(12);
                                }
                                break;
                                case 3: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(221);
                                    if (!(precpred(_ctx, 10)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 10)");
                                    setState(222);
                                    ((BinaryExprContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == RightShift || _la == LeftShift)) {
                                        ((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(223);
                                    expression(11);
                                }
                                break;
                                case 4: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(224);
                                    if (!(precpred(_ctx, 9)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                                    setState(225);
                                    ((BinaryExprContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 960L) != 0))) {
                                        ((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(226);
                                    expression(10);
                                }
                                break;
                                case 5: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(227);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(228);
                                    ((BinaryExprContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == NotEqual || _la == Equal)) {
                                        ((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(229);
                                    expression(9);
                                }
                                break;
                                case 6: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(230);
                                    if (!(precpred(_ctx, 7)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                                    setState(231);
                                    ((BinaryExprContext) _localctx).op = match(BitAnd);
                                    setState(232);
                                    expression(8);
                                }
                                break;
                                case 7: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(233);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(234);
                                    ((BinaryExprContext) _localctx).op = match(BitXor);
                                    setState(235);
                                    expression(7);
                                }
                                break;
                                case 8: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(236);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(237);
                                    ((BinaryExprContext) _localctx).op = match(BitOr);
                                    setState(238);
                                    expression(6);
                                }
                                break;
                                case 9: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(239);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(240);
                                    ((BinaryExprContext) _localctx).op = match(And);
                                    setState(241);
                                    expression(5);
                                }
                                break;
                                case 10: {
                                    _localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(242);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(243);
                                    ((BinaryExprContext) _localctx).op = match(Or);
                                    setState(244);
                                    expression(4);
                                }
                                break;
                                case 11: {
                                    _localctx = new TernaryExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(245);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(246);
                                    ((TernaryExprContext) _localctx).op = match(Question);
                                    setState(247);
                                    expression(0);
                                    setState(248);
                                    match(Colon);
                                    setState(249);
                                    expression(2);
                                }
                                break;
                                case 12: {
                                    _localctx = new AssignExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(251);
                                    if (!(precpred(_ctx, 1)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                    setState(252);
                                    ((AssignExprContext) _localctx).op = match(Assign);
                                    setState(253);
                                    expression(1);
                                }
                                break;
                                case 13: {
                                    _localctx = new ArrayExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(254);
                                    if (!(precpred(_ctx, 19)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 19)");
                                    setState(255);
                                    match(LeftBracket);
                                    setState(256);
                                    expression(0);
                                    setState(257);
                                    match(RightBracket);
                                }
                                break;
                                case 14: {
                                    _localctx = new MemberExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(259);
                                    if (!(precpred(_ctx, 18)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 18)");
                                    setState(260);
                                    ((MemberExprContext) _localctx).op = match(Dot);
                                    setState(261);
                                    match(Identifier);
                                    setState(267);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 27, _ctx)) {
                                        case 1: {
                                            setState(262);
                                            match(LeftParen);
                                            setState(264);
                                            _errHandler.sync(this);
                                            _la = _input.LA(1);
                                            if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 63117466079870982L) != 0)) {
                                                {
                                                    setState(263);
                                                    realParameter();
                                                }
                                            }

                                            setState(266);
                                            match(RightParen);
                                        }
                                        break;
                                    }
                                }
                                break;
                                case 15: {
                                    _localctx = new FuncCallExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(269);
                                    if (!(precpred(_ctx, 17)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 17)");
                                    setState(270);
                                    match(LeftParen);
                                    setState(272);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 63117466079870982L) != 0)) {
                                        {
                                            setState(271);
                                            realParameter();
                                        }
                                    }

                                    setState(274);
                                    match(RightParen);
                                }
                                break;
                                case 16: {
                                    _localctx = new PostExprContext(new ExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(275);
                                    if (!(precpred(_ctx, 16)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 16)");
                                    setState(276);
                                    ((PostExprContext) _localctx).postfix = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == Increase || _la == Decrease)) {
                                        ((PostExprContext) _localctx).postfix = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                }
                                break;
                            }
                        }
                    }
                    setState(281);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 30, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class PrimaryContext extends ParserRuleContext {
        public PrimaryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_primary;
        }

        public PrimaryContext() {
        }

        public void copyFrom(PrimaryContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ParenContext extends PrimaryContext {
        public TerminalNode LeftParen() {
            return getToken(MxParser.LeftParen, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode RightParen() {
            return getToken(MxParser.RightParen, 0);
        }

        public ParenContext(PrimaryContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterParen(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitParen(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitParen(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IdentifyContext extends PrimaryContext {
        public TerminalNode Identifier() {
            return getToken(MxParser.Identifier, 0);
        }

        public IdentifyContext(PrimaryContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterIdentify(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitIdentify(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitIdentify(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LiteralContext extends PrimaryContext {
        public TerminalNode ConstantInt() {
            return getToken(MxParser.ConstantInt, 0);
        }

        public TerminalNode ConstantStr() {
            return getToken(MxParser.ConstantStr, 0);
        }

        public TerminalNode True() {
            return getToken(MxParser.True, 0);
        }

        public TerminalNode False() {
            return getToken(MxParser.False, 0);
        }

        public TerminalNode Null() {
            return getToken(MxParser.Null, 0);
        }

        public TerminalNode This() {
            return getToken(MxParser.This, 0);
        }

        public LiteralContext(PrimaryContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).enterLiteral(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof MxParserListener) ((MxParserListener) listener).exitLiteral(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof MxParserVisitor) return ((MxParserVisitor<? extends T>) visitor).visitLiteral(this);
            else return visitor.visitChildren(this);
        }
    }

    public final PrimaryContext primary() throws RecognitionException {
        PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_primary);
        try {
            setState(293);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case LeftParen:
                    _localctx = new ParenContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(282);
                    match(LeftParen);
                    setState(283);
                    expression(0);
                    setState(284);
                    match(RightParen);
                }
                break;
                case Identifier:
                    _localctx = new IdentifyContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(286);
                    match(Identifier);
                }
                break;
                case ConstantInt:
                    _localctx = new LiteralContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(287);
                    match(ConstantInt);
                }
                break;
                case ConstantStr:
                    _localctx = new LiteralContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(288);
                    match(ConstantStr);
                }
                break;
                case True:
                    _localctx = new LiteralContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                {
                    setState(289);
                    match(True);
                }
                break;
                case False:
                    _localctx = new LiteralContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                {
                    setState(290);
                    match(False);
                }
                break;
                case Null:
                    _localctx = new LiteralContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                {
                    setState(291);
                    match(Null);
                }
                break;
                case This:
                    _localctx = new LiteralContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                {
                    setState(292);
                    match(This);
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 14:
                return expression_sempred((ExpressionContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 12);
            case 1:
                return precpred(_ctx, 11);
            case 2:
                return precpred(_ctx, 10);
            case 3:
                return precpred(_ctx, 9);
            case 4:
                return precpred(_ctx, 8);
            case 5:
                return precpred(_ctx, 7);
            case 6:
                return precpred(_ctx, 6);
            case 7:
                return precpred(_ctx, 5);
            case 8:
                return precpred(_ctx, 4);
            case 9:
                return precpred(_ctx, 3);
            case 10:
                return precpred(_ctx, 2);
            case 11:
                return precpred(_ctx, 1);
            case 12:
                return precpred(_ctx, 19);
            case 13:
                return precpred(_ctx, 18);
            case 14:
                return precpred(_ctx, 17);
            case 15:
                return precpred(_ctx, 16);
        }
        return true;
    }

    public static final String _serializedATN = "\u0004\u0001;\u0128\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002" + "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002" + "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002" + "\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002" + "\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f" + "\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000$\b\u0000\n\u0000\f\u0000" + "\'\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001" + "\u0001\u0001\u0003\u0001/\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001" + "\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u00027\b\u0002\n\u0002\f\u0002" + ":\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004" + "\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004E\b\u0004" + "\n\u0004\f\u0004H\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005" + "\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006" + "\u0001\u0006\u0001\u0006\u0005\u0006V\b\u0006\n\u0006\f\u0006Y\t\u0006" + "\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007" + "`\b\u0007\u0001\b\u0001\b\u0003\bd\b\b\u0001\t\u0001\t\u0003\th\b\t\u0001" + "\t\u0001\t\u0005\tl\b\t\n\t\f\to\t\t\u0001\n\u0001\n\u0001\u000b\u0001" + "\u000b\u0005\u000bu\b\u000b\n\u000b\f\u000bx\t\u000b\u0001\u000b\u0001" + "\u000b\u0001\f\u0001\f\u0001\f\u0005\f\u007f\b\f\n\f\f\f\u0082\t\f\u0001" + "\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003" + "\r\u008d\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001" + "\r\u0001\r\u0001\r\u0003\r\u0099\b\r\u0001\r\u0003\r\u009c\b\r\u0001\r" + "\u0003\r\u009f\b\r\u0001\r\u0001\r\u0003\r\u00a3\b\r\u0001\r\u0001\r\u0001" + "\r\u0001\r\u0003\r\u00a9\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001" + "\r\u0001\r\u0001\r\u0001\r\u0003\r\u00b4\b\r\u0001\u000e\u0001\u000e\u0001" + "\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001" + "\u000e\u0003\u000e\u00bf\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001" + "\u000e\u0004\u000e\u00c5\b\u000e\u000b\u000e\f\u000e\u00c6\u0001\u000e" + "\u0001\u000e\u0005\u000e\u00cb\b\u000e\n\u000e\f\u000e\u00ce\t\u000e\u0001" + "\u000e\u0001\u000e\u0003\u000e\u00d2\b\u000e\u0003\u000e\u00d4\b\u000e" + "\u0003\u000e\u00d6\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" + "\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0109\b\u000e\u0001\u000e" + "\u0003\u000e\u010c\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e" + "\u0111\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u0116\b" + "\u000e\n\u000e\f\u000e\u0119\t\u000e\u0001\u000f\u0001\u000f\u0001\u000f" + "\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f" + "\u0001\u000f\u0001\u000f\u0003\u000f\u0126\b\u000f\u0001\u000f\u0000\u0001" + "\u001c\u0010\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016" + "\u0018\u001a\u001c\u001e\u0000\b\u0001\u0000%\'\u0001\u0000\u0016\u0017" + "\u0003\u0000\u0001\u0002\u000e\u000e\u0014\u0014\u0001\u0000\u0003\u0005" + "\u0001\u0000\u0001\u0002\u0001\u0000\u000f\u0010\u0001\u0000\u0006\t\u0001" + "\u0000\n\u000b\u0157\u0000%\u0001\u0000\u0000\u0000\u0002*\u0001\u0000" + "\u0000\u0000\u00043\u0001\u0000\u0000\u0000\u0006;\u0001\u0000\u0000\u0000" + "\b>\u0001\u0000\u0000\u0000\nL\u0001\u0000\u0000\u0000\fQ\u0001\u0000" + "\u0000\u0000\u000e\\\u0001\u0000\u0000\u0000\u0010c\u0001\u0000\u0000" + "\u0000\u0012g\u0001\u0000\u0000\u0000\u0014p\u0001\u0000\u0000\u0000\u0016" + "r\u0001\u0000\u0000\u0000\u0018{\u0001\u0000\u0000\u0000\u001a\u00b3\u0001" + "\u0000\u0000\u0000\u001c\u00d5\u0001\u0000\u0000\u0000\u001e\u0125\u0001" + "\u0000\u0000\u0000 $\u0003\u0002\u0001\u0000!$\u0003\b\u0004\u0000\"$" + "\u0003\f\u0006\u0000# \u0001\u0000\u0000\u0000#!\u0001\u0000\u0000\u0000" + "#\"\u0001\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000%#\u0001\u0000\u0000" + "\u0000%&\u0001\u0000\u0000\u0000&(\u0001\u0000\u0000\u0000\'%\u0001\u0000" + "\u0000\u0000()\u0005\u0000\u0000\u0001)\u0001\u0001\u0000\u0000\u0000" + "*+\u0003\u0010\b\u0000+,\u00055\u0000\u0000,.\u0005\u001e\u0000\u0000" + "-/\u0003\u0004\u0002\u0000.-\u0001\u0000\u0000\u0000./\u0001\u0000\u0000" + "\u0000/0\u0001\u0000\u0000\u000001\u0005\u001f\u0000\u000012\u0003\u0016" + "\u000b\u00002\u0003\u0001\u0000\u0000\u000038\u0003\u0006\u0003\u0000" + "45\u0005\u001c\u0000\u000057\u0003\u0006\u0003\u000064\u0001\u0000\u0000" + "\u00007:\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u000089\u0001\u0000" + "\u0000\u00009\u0005\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000" + ";<\u0003\u0012\t\u0000<=\u00055\u0000\u0000=\u0007\u0001\u0000\u0000\u0000" + ">?\u0005)\u0000\u0000?@\u00055\u0000\u0000@F\u0005\"\u0000\u0000AE\u0003" + "\f\u0006\u0000BE\u0003\n\u0005\u0000CE\u0003\u0002\u0001\u0000DA\u0001" + "\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000DC\u0001\u0000\u0000\u0000" + "EH\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000FG\u0001\u0000\u0000" + "\u0000GI\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000IJ\u0005#\u0000" + "\u0000JK\u0005\u001b\u0000\u0000K\t\u0001\u0000\u0000\u0000LM\u00055\u0000" + "\u0000MN\u0005\u001e\u0000\u0000NO\u0005\u001f\u0000\u0000OP\u0003\u0016" + "\u000b\u0000P\u000b\u0001\u0000\u0000\u0000QR\u0003\u0012\t\u0000RW\u0003" + "\u000e\u0007\u0000ST\u0005\u001c\u0000\u0000TV\u0003\u000e\u0007\u0000" + "US\u0001\u0000\u0000\u0000VY\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000" + "\u0000WX\u0001\u0000\u0000\u0000XZ\u0001\u0000\u0000\u0000YW\u0001\u0000" + "\u0000\u0000Z[\u0005\u001b\u0000\u0000[\r\u0001\u0000\u0000\u0000\\_\u0005" + "5\u0000\u0000]^\u0005\u0015\u0000\u0000^`\u0003\u001c\u000e\u0000_]\u0001" + "\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`\u000f\u0001\u0000\u0000" + "\u0000ad\u0005$\u0000\u0000bd\u0003\u0012\t\u0000ca\u0001\u0000\u0000" + "\u0000cb\u0001\u0000\u0000\u0000d\u0011\u0001\u0000\u0000\u0000eh\u0003" + "\u0014\n\u0000fh\u00055\u0000\u0000ge\u0001\u0000\u0000\u0000gf\u0001" + "\u0000\u0000\u0000hm\u0001\u0000\u0000\u0000ij\u0005 \u0000\u0000jl\u0005" + "!\u0000\u0000ki\u0001\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000mk\u0001" + "\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000n\u0013\u0001\u0000\u0000" + "\u0000om\u0001\u0000\u0000\u0000pq\u0007\u0000\u0000\u0000q\u0015\u0001" + "\u0000\u0000\u0000rv\u0005\"\u0000\u0000su\u0003\u001a\r\u0000ts\u0001" + "\u0000\u0000\u0000ux\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000" + "vw\u0001\u0000\u0000\u0000wy\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000" + "\u0000yz\u0005#\u0000\u0000z\u0017\u0001\u0000\u0000\u0000{\u0080\u0003" + "\u001c\u000e\u0000|}\u0005\u001c\u0000\u0000}\u007f\u0003\u001c\u000e" + "\u0000~|\u0001\u0000\u0000\u0000\u007f\u0082\u0001\u0000\u0000\u0000\u0080" + "~\u0001\u0000\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081\u0019" + "\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0083\u00b4" + "\u0003\u0016\u000b\u0000\u0084\u00b4\u0003\f\u0006\u0000\u0085\u0086\u0005" + ".\u0000\u0000\u0086\u0087\u0005\u001e\u0000\u0000\u0087\u0088\u0003\u001c" + "\u000e\u0000\u0088\u0089\u0005\u001f\u0000\u0000\u0089\u008c\u0003\u001a" + "\r\u0000\u008a\u008b\u0005/\u0000\u0000\u008b\u008d\u0003\u001a\r\u0000" + "\u008c\u008a\u0001\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000\u0000" + "\u008d\u00b4\u0001\u0000\u0000\u0000\u008e\u008f\u00051\u0000\u0000\u008f" + "\u0090\u0005\u001e\u0000\u0000\u0090\u0091\u0003\u001c\u000e\u0000\u0091" + "\u0092\u0005\u001f\u0000\u0000\u0092\u0093\u0003\u001a\r\u0000\u0093\u00b4" + "\u0001\u0000\u0000\u0000\u0094\u0095\u00050\u0000\u0000\u0095\u009b\u0005" + "\u001e\u0000\u0000\u0096\u009c\u0003\f\u0006\u0000\u0097\u0099\u0003\u001c" + "\u000e\u0000\u0098\u0097\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000" + "\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u009c\u0005\u001b" + "\u0000\u0000\u009b\u0096\u0001\u0000\u0000\u0000\u009b\u0098\u0001\u0000" + "\u0000\u0000\u009c\u009e\u0001\u0000\u0000\u0000\u009d\u009f\u0003\u001c" + "\u000e\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000" + "\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a2\u0005\u001b" + "\u0000\u0000\u00a1\u00a3\u0003\u001c\u000e\u0000\u00a2\u00a1\u0001\u0000" + "\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000" + "\u0000\u0000\u00a4\u00a5\u0005\u001f\u0000\u0000\u00a5\u00b4\u0003\u001a" + "\r\u0000\u00a6\u00a8\u00054\u0000\u0000\u00a7\u00a9\u0003\u001c\u000e" + "\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000" + "\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000\u00aa\u00b4\u0005\u001b\u0000" + "\u0000\u00ab\u00ac\u00052\u0000\u0000\u00ac\u00b4\u0005\u001b\u0000\u0000" + "\u00ad\u00ae\u00053\u0000\u0000\u00ae\u00b4\u0005\u001b\u0000\u0000\u00af" + "\u00b0\u0003\u001c\u000e\u0000\u00b0\u00b1\u0005\u001b\u0000\u0000\u00b1" + "\u00b4\u0001\u0000\u0000\u0000\u00b2\u00b4\u0005\u001b\u0000\u0000\u00b3" + "\u0083\u0001\u0000\u0000\u0000\u00b3\u0084\u0001\u0000\u0000\u0000\u00b3" + "\u0085\u0001\u0000\u0000\u0000\u00b3\u008e\u0001\u0000\u0000\u0000\u00b3" + "\u0094\u0001\u0000\u0000\u0000\u00b3\u00a6\u0001\u0000\u0000\u0000\u00b3" + "\u00ab\u0001\u0000\u0000\u0000\u00b3\u00ad\u0001\u0000\u0000\u0000\u00b3" + "\u00af\u0001\u0000\u0000\u0000\u00b3\u00b2\u0001\u0000\u0000\u0000\u00b4" + "\u001b\u0001\u0000\u0000\u0000\u00b5\u00b6\u0006\u000e\uffff\uffff\u0000" + "\u00b6\u00d6\u0003\u001e\u000f\u0000\u00b7\u00b8\u0007\u0001\u0000\u0000" + "\u00b8\u00d6\u0003\u001c\u000e\u000f\u00b9\u00ba\u0007\u0002\u0000\u0000" + "\u00ba\u00d6\u0003\u001c\u000e\u000e\u00bb\u00be\u0005(\u0000\u0000\u00bc" + "\u00bf\u0003\u0014\n\u0000\u00bd\u00bf\u00055\u0000\u0000\u00be\u00bc" + "\u0001\u0000\u0000\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00bf\u00d3" + "\u0001\u0000\u0000\u0000\u00c0\u00c1\u0005 \u0000\u0000\u00c1\u00c2\u0003" + "\u001c\u000e\u0000\u00c2\u00c3\u0005!\u0000\u0000\u00c3\u00c5\u0001\u0000" + "\u0000\u0000\u00c4\u00c0\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001\u0000" + "\u0000\u0000\u00c6\u00c4\u0001\u0000\u0000\u0000\u00c6\u00c7\u0001\u0000" + "\u0000\u0000\u00c7\u00cc\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005 \u0000" + "\u0000\u00c9\u00cb\u0005!\u0000\u0000\u00ca\u00c8\u0001\u0000\u0000\u0000" + "\u00cb\u00ce\u0001\u0000\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000" + "\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd\u00d4\u0001\u0000\u0000\u0000" + "\u00ce\u00cc\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005\u001e\u0000\u0000" + "\u00d0\u00d2\u0005\u001f\u0000\u0000\u00d1\u00cf\u0001\u0000\u0000\u0000" + "\u00d1\u00d2\u0001\u0000\u0000\u0000\u00d2\u00d4\u0001\u0000\u0000\u0000" + "\u00d3\u00c4\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001\u0000\u0000\u0000" + "\u00d4\u00d6\u0001\u0000\u0000\u0000\u00d5\u00b5\u0001\u0000\u0000\u0000" + "\u00d5\u00b7\u0001\u0000\u0000\u0000\u00d5\u00b9\u0001\u0000\u0000\u0000" + "\u00d5\u00bb\u0001\u0000\u0000\u0000\u00d6\u0117\u0001\u0000\u0000\u0000" + "\u00d7\u00d8\n\f\u0000\u0000\u00d8\u00d9\u0007\u0003\u0000\u0000\u00d9" + "\u0116\u0003\u001c\u000e\r\u00da\u00db\n\u000b\u0000\u0000\u00db\u00dc" + "\u0007\u0004\u0000\u0000\u00dc\u0116\u0003\u001c\u000e\f\u00dd\u00de\n" + "\n\u0000\u0000\u00de\u00df\u0007\u0005\u0000\u0000\u00df\u0116\u0003\u001c" + "\u000e\u000b\u00e0\u00e1\n\t\u0000\u0000\u00e1\u00e2\u0007\u0006\u0000" + "\u0000\u00e2\u0116\u0003\u001c\u000e\n\u00e3\u00e4\n\b\u0000\u0000\u00e4" + "\u00e5\u0007\u0007\u0000\u0000\u00e5\u0116\u0003\u001c\u000e\t\u00e6\u00e7" + "\n\u0007\u0000\u0000\u00e7\u00e8\u0005\u0011\u0000\u0000\u00e8\u0116\u0003" + "\u001c\u000e\b\u00e9\u00ea\n\u0006\u0000\u0000\u00ea\u00eb\u0005\u0013" + "\u0000\u0000\u00eb\u0116\u0003\u001c\u000e\u0007\u00ec\u00ed\n\u0005\u0000" + "\u0000\u00ed\u00ee\u0005\u0012\u0000\u0000\u00ee\u0116\u0003\u001c\u000e" + "\u0006\u00ef\u00f0\n\u0004\u0000\u0000\u00f0\u00f1\u0005\f\u0000\u0000" + "\u00f1\u0116\u0003\u001c\u000e\u0005\u00f2\u00f3\n\u0003\u0000\u0000\u00f3" + "\u00f4\u0005\r\u0000\u0000\u00f4\u0116\u0003\u001c\u000e\u0004\u00f5\u00f6" + "\n\u0002\u0000\u0000\u00f6\u00f7\u0005\u0019\u0000\u0000\u00f7\u00f8\u0003" + "\u001c\u000e\u0000\u00f8\u00f9\u0005\u001a\u0000\u0000\u00f9\u00fa\u0003" + "\u001c\u000e\u0002\u00fa\u0116\u0001\u0000\u0000\u0000\u00fb\u00fc\n\u0001" + "\u0000\u0000\u00fc\u00fd\u0005\u0015\u0000\u0000\u00fd\u0116\u0003\u001c" + "\u000e\u0001\u00fe\u00ff\n\u0013\u0000\u0000\u00ff\u0100\u0005 \u0000" + "\u0000\u0100\u0101\u0003\u001c\u000e\u0000\u0101\u0102\u0005!\u0000\u0000" + "\u0102\u0116\u0001\u0000\u0000\u0000\u0103\u0104\n\u0012\u0000\u0000\u0104" + "\u0105\u0005\u0018\u0000\u0000\u0105\u010b\u00055\u0000\u0000\u0106\u0108" + "\u0005\u001e\u0000\u0000\u0107\u0109\u0003\u0018\f\u0000\u0108\u0107\u0001" + "\u0000\u0000\u0000\u0108\u0109\u0001\u0000\u0000\u0000\u0109\u010a\u0001" + "\u0000\u0000\u0000\u010a\u010c\u0005\u001f\u0000\u0000\u010b\u0106\u0001" + "\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000\u010c\u0116\u0001" + "\u0000\u0000\u0000\u010d\u010e\n\u0011\u0000\u0000\u010e\u0110\u0005\u001e" + "\u0000\u0000\u010f\u0111\u0003\u0018\f\u0000\u0110\u010f\u0001\u0000\u0000" + "\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u0112\u0001\u0000\u0000" + "\u0000\u0112\u0116\u0005\u001f\u0000\u0000\u0113\u0114\n\u0010\u0000\u0000" + "\u0114\u0116\u0007\u0001\u0000\u0000\u0115\u00d7\u0001\u0000\u0000\u0000" + "\u0115\u00da\u0001\u0000\u0000\u0000\u0115\u00dd\u0001\u0000\u0000\u0000" + "\u0115\u00e0\u0001\u0000\u0000\u0000\u0115\u00e3\u0001\u0000\u0000\u0000" + "\u0115\u00e6\u0001\u0000\u0000\u0000\u0115\u00e9\u0001\u0000\u0000\u0000" + "\u0115\u00ec\u0001\u0000\u0000\u0000\u0115\u00ef\u0001\u0000\u0000\u0000" + "\u0115\u00f2\u0001\u0000\u0000\u0000\u0115\u00f5\u0001\u0000\u0000\u0000" + "\u0115\u00fb\u0001\u0000\u0000\u0000\u0115\u00fe\u0001\u0000\u0000\u0000" + "\u0115\u0103\u0001\u0000\u0000\u0000\u0115\u010d\u0001\u0000\u0000\u0000" + "\u0115\u0113\u0001\u0000\u0000\u0000\u0116\u0119\u0001\u0000\u0000\u0000" + "\u0117\u0115\u0001\u0000\u0000\u0000\u0117\u0118\u0001\u0000\u0000\u0000" + "\u0118\u001d\u0001\u0000\u0000\u0000\u0119\u0117\u0001\u0000\u0000\u0000" + "\u011a\u011b\u0005\u001e\u0000\u0000\u011b\u011c\u0003\u001c\u000e\u0000" + "\u011c\u011d\u0005\u001f\u0000\u0000\u011d\u0126\u0001\u0000\u0000\u0000" + "\u011e\u0126\u00055\u0000\u0000\u011f\u0126\u00056\u0000\u0000\u0120\u0126" + "\u00057\u0000\u0000\u0121\u0126\u0005+\u0000\u0000\u0122\u0126\u0005," + "\u0000\u0000\u0123\u0126\u0005*\u0000\u0000\u0124\u0126\u0005-\u0000\u0000" + "\u0125\u011a\u0001\u0000\u0000\u0000\u0125\u011e\u0001\u0000\u0000\u0000" + "\u0125\u011f\u0001\u0000\u0000\u0000\u0125\u0120\u0001\u0000\u0000\u0000" + "\u0125\u0121\u0001\u0000\u0000\u0000\u0125\u0122\u0001\u0000\u0000\u0000" + "\u0125\u0123\u0001\u0000\u0000\u0000\u0125\u0124\u0001\u0000\u0000\u0000" + "\u0126\u001f\u0001\u0000\u0000\u0000 #%.8DFW_cgmv\u0080\u008c\u0098\u009b" + "\u009e\u00a2\u00a8\u00b3\u00be\u00c6\u00cc\u00d1\u00d3\u00d5\u0108\u010b" + "\u0110\u0115\u0117\u0125";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}