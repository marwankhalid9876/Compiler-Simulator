package csen1002.main.task8;// Generated from grammars\Task8.g4 by ANTLR 4.12.0
// Generated from grammars\Task8.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class Task8Lexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	public static final int
			IF=1, ELSE=2, COMP=3, ID=4, NUM=5, LIT=6, LP=7, RP=8, WS=9;
	public static String[] channelNames = {
			"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
			"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
				"IF", "ELSE", "COMP", "ID", "NUM", "DIGITS", "EXPONENT", "LIT", "ESC",
				"LP", "RP", "WS", "DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
				null, null, null, null, null, null, null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
				null, "IF", "ELSE", "COMP", "ID", "NUM", "LIT", "LP", "RP", "WS"
		};
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


	public Task8Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Task8.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
			"\u0004\u0000\t\u00a9\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
					"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
					"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
					"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
					"\u0007\u000b\u0002\f\u0007\f\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
					"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000$\b"+
					"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
					"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001f\b"+
					"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
					"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002q\b\u0002\u0001"+
					"\u0003\u0001\u0003\u0005\u0003u\b\u0003\n\u0003\f\u0003x\t\u0003\u0001"+
					"\u0004\u0001\u0004\u0001\u0004\u0003\u0004}\b\u0004\u0001\u0004\u0003"+
					"\u0004\u0080\b\u0004\u0001\u0005\u0004\u0005\u0083\b\u0005\u000b\u0005"+
					"\f\u0005\u0084\u0001\u0006\u0001\u0006\u0003\u0006\u0089\b\u0006\u0001"+
					"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0090"+
					"\b\u0007\n\u0007\f\u0007\u0093\t\u0007\u0001\u0007\u0001\u0007\u0001\b"+
					"\u0001\b\u0001\b\u0001\b\u0003\b\u009b\b\b\u0001\t\u0001\t\u0001\n\u0001"+
					"\n\u0001\u000b\u0004\u000b\u00a2\b\u000b\u000b\u000b\f\u000b\u00a3\u0001"+
					"\u000b\u0001\u000b\u0001\f\u0001\f\u0000\u0000\r\u0001\u0001\u0003\u0002"+
					"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0000\r\u0000\u000f\u0006\u0011"+
					"\u0000\u0013\u0007\u0015\b\u0017\t\u0019\u0000\u0001\u0000\b\u0002\u0000"+
					"<<>>\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001\u000009\u0002\u0000E"+
					"Eee\u0002\u0000++--\u0004\u0000\n\n\r\r\"\"\\\\\u0003\u0000\t\n\r\r  "+
					"\u00c3\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
					"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
					"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
					"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
					"\u0000\u0017\u0001\u0000\u0000\u0000\u0001#\u0001\u0000\u0000\u0000\u0003"+
					"e\u0001\u0000\u0000\u0000\u0005p\u0001\u0000\u0000\u0000\u0007r\u0001"+
					"\u0000\u0000\u0000\ty\u0001\u0000\u0000\u0000\u000b\u0082\u0001\u0000"+
					"\u0000\u0000\r\u0086\u0001\u0000\u0000\u0000\u000f\u008c\u0001\u0000\u0000"+
					"\u0000\u0011\u009a\u0001\u0000\u0000\u0000\u0013\u009c\u0001\u0000\u0000"+
					"\u0000\u0015\u009e\u0001\u0000\u0000\u0000\u0017\u00a1\u0001\u0000\u0000"+
					"\u0000\u0019\u00a7\u0001\u0000\u0000\u0000\u001b\u001c\u0005i\u0000\u0000"+
					"\u001c$\u0005f\u0000\u0000\u001d\u001e\u0005I\u0000\u0000\u001e$\u0005"+
					"f\u0000\u0000\u001f \u0005i\u0000\u0000 $\u0005F\u0000\u0000!\"\u0005"+
					"I\u0000\u0000\"$\u0005F\u0000\u0000#\u001b\u0001\u0000\u0000\u0000#\u001d"+
					"\u0001\u0000\u0000\u0000#\u001f\u0001\u0000\u0000\u0000#!\u0001\u0000"+
					"\u0000\u0000$\u0002\u0001\u0000\u0000\u0000%&\u0005e\u0000\u0000&\'\u0005"+
					"l\u0000\u0000\'(\u0005s\u0000\u0000(f\u0005e\u0000\u0000)*\u0005e\u0000"+
					"\u0000*+\u0005l\u0000\u0000+,\u0005s\u0000\u0000,f\u0005E\u0000\u0000"+
					"-.\u0005e\u0000\u0000./\u0005l\u0000\u0000/0\u0005S\u0000\u00000f\u0005"+
					"e\u0000\u000012\u0005e\u0000\u000023\u0005l\u0000\u000034\u0005S\u0000"+
					"\u00004f\u0005E\u0000\u000056\u0005e\u0000\u000067\u0005L\u0000\u0000"+
					"78\u0005s\u0000\u00008f\u0005e\u0000\u00009:\u0005e\u0000\u0000:;\u0005"+
					"L\u0000\u0000;<\u0005s\u0000\u0000<f\u0005E\u0000\u0000=>\u0005e\u0000"+
					"\u0000>?\u0005L\u0000\u0000?@\u0005S\u0000\u0000@f\u0005e\u0000\u0000"+
					"AB\u0005e\u0000\u0000BC\u0005L\u0000\u0000CD\u0005S\u0000\u0000Df\u0005"+
					"E\u0000\u0000EF\u0005E\u0000\u0000FG\u0005l\u0000\u0000GH\u0005s\u0000"+
					"\u0000Hf\u0005e\u0000\u0000IJ\u0005E\u0000\u0000JK\u0005l\u0000\u0000"+
					"KL\u0005s\u0000\u0000Lf\u0005E\u0000\u0000MN\u0005E\u0000\u0000NO\u0005"+
					"l\u0000\u0000OP\u0005S\u0000\u0000Pf\u0005e\u0000\u0000QR\u0005E\u0000"+
					"\u0000RS\u0005l\u0000\u0000ST\u0005S\u0000\u0000Tf\u0005E\u0000\u0000"+
					"UV\u0005E\u0000\u0000VW\u0005L\u0000\u0000WX\u0005s\u0000\u0000Xf\u0005"+
					"e\u0000\u0000YZ\u0005E\u0000\u0000Z[\u0005L\u0000\u0000[\\\u0005s\u0000"+
					"\u0000\\f\u0005E\u0000\u0000]^\u0005E\u0000\u0000^_\u0005L\u0000\u0000"+
					"_`\u0005S\u0000\u0000`f\u0005e\u0000\u0000ab\u0005E\u0000\u0000bc\u0005"+
					"L\u0000\u0000cd\u0005S\u0000\u0000df\u0005E\u0000\u0000e%\u0001\u0000"+
					"\u0000\u0000e)\u0001\u0000\u0000\u0000e-\u0001\u0000\u0000\u0000e1\u0001"+
					"\u0000\u0000\u0000e5\u0001\u0000\u0000\u0000e9\u0001\u0000\u0000\u0000"+
					"e=\u0001\u0000\u0000\u0000eA\u0001\u0000\u0000\u0000eE\u0001\u0000\u0000"+
					"\u0000eI\u0001\u0000\u0000\u0000eM\u0001\u0000\u0000\u0000eQ\u0001\u0000"+
					"\u0000\u0000eU\u0001\u0000\u0000\u0000eY\u0001\u0000\u0000\u0000e]\u0001"+
					"\u0000\u0000\u0000ea\u0001\u0000\u0000\u0000f\u0004\u0001\u0000\u0000"+
					"\u0000gq\u0007\u0000\u0000\u0000hi\u0005>\u0000\u0000iq\u0005=\u0000\u0000"+
					"jk\u0005<\u0000\u0000kq\u0005=\u0000\u0000lm\u0005=\u0000\u0000mq\u0005"+
					"=\u0000\u0000no\u0005!\u0000\u0000oq\u0005=\u0000\u0000pg\u0001\u0000"+
					"\u0000\u0000ph\u0001\u0000\u0000\u0000pj\u0001\u0000\u0000\u0000pl\u0001"+
					"\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000q\u0006\u0001\u0000\u0000"+
					"\u0000rv\u0007\u0001\u0000\u0000su\u0007\u0002\u0000\u0000ts\u0001\u0000"+
					"\u0000\u0000ux\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000vw\u0001"+
					"\u0000\u0000\u0000w\b\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000"+
					"y|\u0003\u000b\u0005\u0000z{\u0005.\u0000\u0000{}\u0003\u000b\u0005\u0000"+
					"|z\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000}\u007f\u0001\u0000"+
					"\u0000\u0000~\u0080\u0003\r\u0006\u0000\u007f~\u0001\u0000\u0000\u0000"+
					"\u007f\u0080\u0001\u0000\u0000\u0000\u0080\n\u0001\u0000\u0000\u0000\u0081"+
					"\u0083\u0007\u0003\u0000\u0000\u0082\u0081\u0001\u0000\u0000\u0000\u0083"+
					"\u0084\u0001\u0000\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0084"+
					"\u0085\u0001\u0000\u0000\u0000\u0085\f\u0001\u0000\u0000\u0000\u0086\u0088"+
					"\u0007\u0004\u0000\u0000\u0087\u0089\u0007\u0005\u0000\u0000\u0088\u0087"+
					"\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089\u008a"+
					"\u0001\u0000\u0000\u0000\u008a\u008b\u0003\u000b\u0005\u0000\u008b\u000e"+
					"\u0001\u0000\u0000\u0000\u008c\u0091\u0005\"\u0000\u0000\u008d\u0090\u0003"+
					"\u0011\b\u0000\u008e\u0090\b\u0006\u0000\u0000\u008f\u008d\u0001\u0000"+
					"\u0000\u0000\u008f\u008e\u0001\u0000\u0000\u0000\u0090\u0093\u0001\u0000"+
					"\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0091\u0092\u0001\u0000"+
					"\u0000\u0000\u0092\u0094\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000"+
					"\u0000\u0000\u0094\u0095\u0005\"\u0000\u0000\u0095\u0010\u0001\u0000\u0000"+
					"\u0000\u0096\u0097\u0005\\\u0000\u0000\u0097\u009b\u0005\\\u0000\u0000"+
					"\u0098\u0099\u0005\\\u0000\u0000\u0099\u009b\u0005\"\u0000\u0000\u009a"+
					"\u0096\u0001\u0000\u0000\u0000\u009a\u0098\u0001\u0000\u0000\u0000\u009b"+
					"\u0012\u0001\u0000\u0000\u0000\u009c\u009d\u0005(\u0000\u0000\u009d\u0014"+
					"\u0001\u0000\u0000\u0000\u009e\u009f\u0005)\u0000\u0000\u009f\u0016\u0001"+
					"\u0000\u0000\u0000\u00a0\u00a2\u0007\u0007\u0000\u0000\u00a1\u00a0\u0001"+
					"\u0000\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a1\u0001"+
					"\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001"+
					"\u0000\u0000\u0000\u00a5\u00a6\u0006\u000b\u0000\u0000\u00a6\u0018\u0001"+
					"\u0000\u0000\u0000\u00a7\u00a8\u0007\u0003\u0000\u0000\u00a8\u001a\u0001"+
					"\u0000\u0000\u0000\r\u0000#epv|\u007f\u0084\u0088\u008f\u0091\u009a\u00a3"+
					"\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}