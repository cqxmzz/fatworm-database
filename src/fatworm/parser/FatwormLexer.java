// $ANTLR 3.5 Fatworm.g 2013-03-22 15:40:43
package fatworm.parser;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

@SuppressWarnings("all")
public class FatwormLexer extends Lexer
{
	public static final int EOF = -1;

	public static final int T__105 = 105;

	public static final int T__106 = 106;

	public static final int T__107 = 107;

	public static final int T__108 = 108;

	public static final int T__109 = 109;

	public static final int T__110 = 110;

	public static final int T__111 = 111;

	public static final int T__112 = 112;

	public static final int T__113 = 113;

	public static final int T__114 = 114;

	public static final int T__115 = 115;

	public static final int T__116 = 116;

	public static final int T__117 = 117;

	public static final int T__118 = 118;

	public static final int T__119 = 119;

	public static final int A = 4;

	public static final int ALL = 5;

	public static final int AND = 6;

	public static final int ANY = 7;

	public static final int AS = 8;

	public static final int ASC = 9;

	public static final int AUTO_INCREMENT = 10;

	public static final int AVG = 11;

	public static final int B = 12;

	public static final int BOOLEAN = 13;

	public static final int BY = 14;

	public static final int C = 15;

	public static final int CHAR = 16;

	public static final int COUNT = 17;

	public static final int CREATE = 18;

	public static final int CREATE_DATABASE = 19;

	public static final int CREATE_DEFINITION = 20;

	public static final int CREATE_INDEX = 21;

	public static final int CREATE_TABLE = 22;

	public static final int CREATE_UNIQUE_INDEX = 23;

	public static final int D = 24;

	public static final int DATABASE = 25;

	public static final int DATETIME = 26;

	public static final int DECIMAL = 27;

	public static final int DEFAULT = 28;

	public static final int DELETE = 29;

	public static final int DESC = 30;

	public static final int DIGIT = 31;

	public static final int DISTINCT = 32;

	public static final int DROP = 33;

	public static final int DROP_DATABASE = 34;

	public static final int DROP_INDEX = 35;

	public static final int DROP_TABLE = 36;

	public static final int E = 37;

	public static final int EXISTS = 38;

	public static final int F = 39;

	public static final int FALSE = 40;

	public static final int FLOAT = 41;

	public static final int FLOAT_LITERAL = 42;

	public static final int FROM = 43;

	public static final int G = 44;

	public static final int GROUP = 45;

	public static final int H = 46;

	public static final int HAVING = 47;

	public static final int I = 48;

	public static final int ID = 49;

	public static final int IN = 50;

	public static final int INDEX = 51;

	public static final int INSERT = 52;

	public static final int INSERT_COLUMNS = 53;

	public static final int INSERT_SUBQUERY = 54;

	public static final int INSERT_VALUES = 55;

	public static final int INT = 56;

	public static final int INTEGER_LITERAL = 57;

	public static final int INTO = 58;

	public static final int J = 59;

	public static final int K = 60;

	public static final int KEY = 61;

	public static final int L = 62;

	public static final int LETTER = 63;

	public static final int M = 64;

	public static final int MAX = 65;

	public static final int MIN = 66;

	public static final int N = 67;

	public static final int NOT = 68;

	public static final int NOT_EXISTS = 69;

	public static final int NULL = 70;

	public static final int O = 71;

	public static final int ON = 72;

	public static final int OR = 73;

	public static final int ORDER = 74;

	public static final int P = 75;

	public static final int PRIMARY = 76;

	public static final int PRIMARY_KEY = 77;

	public static final int Q = 78;

	public static final int R = 79;

	public static final int S = 80;

	public static final int SELECT = 81;

	public static final int SELECT_DISTINCT = 82;

	public static final int SET = 83;

	public static final int STRING_LITERAL = 84;

	public static final int SUM = 85;

	public static final int T = 86;

	public static final int TABLE = 87;

	public static final int TIMESTAMP = 88;

	public static final int TRUE = 89;

	public static final int U = 90;

	public static final int UNIQUE = 91;

	public static final int UPDATE = 92;

	public static final int UPDATE_PAIR = 93;

	public static final int USE = 94;

	public static final int USE_DATABASE = 95;

	public static final int V = 96;

	public static final int VALUES = 97;

	public static final int VARCHAR = 98;

	public static final int W = 99;

	public static final int WHERE = 100;

	public static final int WS = 101;

	public static final int X = 102;

	public static final int Y = 103;

	public static final int Z = 104;

	// delegates
	// delegators
	public Lexer[] getDelegates()
	{
		return new Lexer[]
		{};
	}

	public FatwormLexer()
	{
	}

	public FatwormLexer(CharStream input)
	{
		this(input, new RecognizerSharedState());
	}

	public FatwormLexer(CharStream input, RecognizerSharedState state)
	{
		super(input, state);
	}

	@Override
	public String getGrammarFileName()
	{
		return "Fatworm.g";
	}

	// $ANTLR start "T__105"
	public final void mT__105() throws RecognitionException
	{
		try
		{
			int _type = T__105;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:4:8: ( '%' )
			// Fatworm.g:4:10: '%'
			{
				match('%');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__105"
	// $ANTLR start "T__106"
	public final void mT__106() throws RecognitionException
	{
		try
		{
			int _type = T__106;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:5:8: ( '(' )
			// Fatworm.g:5:10: '('
			{
				match('(');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__106"
	// $ANTLR start "T__107"
	public final void mT__107() throws RecognitionException
	{
		try
		{
			int _type = T__107;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:6:8: ( ')' )
			// Fatworm.g:6:10: ')'
			{
				match(')');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__107"
	// $ANTLR start "T__108"
	public final void mT__108() throws RecognitionException
	{
		try
		{
			int _type = T__108;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:7:8: ( '*' )
			// Fatworm.g:7:10: '*'
			{
				match('*');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__108"
	// $ANTLR start "T__109"
	public final void mT__109() throws RecognitionException
	{
		try
		{
			int _type = T__109;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:8:8: ( '+' )
			// Fatworm.g:8:10: '+'
			{
				match('+');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__109"
	// $ANTLR start "T__110"
	public final void mT__110() throws RecognitionException
	{
		try
		{
			int _type = T__110;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:9:8: ( ',' )
			// Fatworm.g:9:10: ','
			{
				match(',');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__110"
	// $ANTLR start "T__111"
	public final void mT__111() throws RecognitionException
	{
		try
		{
			int _type = T__111;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:10:8: ( '-' )
			// Fatworm.g:10:10: '-'
			{
				match('-');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__111"
	// $ANTLR start "T__112"
	public final void mT__112() throws RecognitionException
	{
		try
		{
			int _type = T__112;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:11:8: ( '.' )
			// Fatworm.g:11:10: '.'
			{
				match('.');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__112"
	// $ANTLR start "T__113"
	public final void mT__113() throws RecognitionException
	{
		try
		{
			int _type = T__113;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:12:8: ( '/' )
			// Fatworm.g:12:10: '/'
			{
				match('/');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__113"
	// $ANTLR start "T__114"
	public final void mT__114() throws RecognitionException
	{
		try
		{
			int _type = T__114;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:13:8: ( '<' )
			// Fatworm.g:13:10: '<'
			{
				match('<');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__114"
	// $ANTLR start "T__115"
	public final void mT__115() throws RecognitionException
	{
		try
		{
			int _type = T__115;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:14:8: ( '<=' )
			// Fatworm.g:14:10: '<='
			{
				match("<=");
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__115"
	// $ANTLR start "T__116"
	public final void mT__116() throws RecognitionException
	{
		try
		{
			int _type = T__116;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:15:8: ( '<>' )
			// Fatworm.g:15:10: '<>'
			{
				match("<>");
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__116"
	// $ANTLR start "T__117"
	public final void mT__117() throws RecognitionException
	{
		try
		{
			int _type = T__117;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:16:8: ( '=' )
			// Fatworm.g:16:10: '='
			{
				match('=');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__117"
	// $ANTLR start "T__118"
	public final void mT__118() throws RecognitionException
	{
		try
		{
			int _type = T__118;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:17:8: ( '>' )
			// Fatworm.g:17:10: '>'
			{
				match('>');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__118"
	// $ANTLR start "T__119"
	public final void mT__119() throws RecognitionException
	{
		try
		{
			int _type = T__119;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:18:8: ( '>=' )
			// Fatworm.g:18:10: '>='
			{
				match(">=");
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T__119"
	// $ANTLR start "SELECT"
	public final void mSELECT() throws RecognitionException
	{
		try
		{
			int _type = SELECT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:267:8: ( S E L E C T )
			// Fatworm.g:267:10: S E L E C T
			{
				mS();
				mE();
				mL();
				mE();
				mC();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "SELECT"
	// $ANTLR start "CREATE"
	public final void mCREATE() throws RecognitionException
	{
		try
		{
			int _type = CREATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:268:8: ( C R E A T E )
			// Fatworm.g:268:10: C R E A T E
			{
				mC();
				mR();
				mE();
				mA();
				mT();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "CREATE"
	// $ANTLR start "DATABASE"
	public final void mDATABASE() throws RecognitionException
	{
		try
		{
			int _type = DATABASE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:269:10: ( D A T A B A S E )
			// Fatworm.g:269:12: D A T A B A S E
			{
				mD();
				mA();
				mT();
				mA();
				mB();
				mA();
				mS();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DATABASE"
	// $ANTLR start "DROP"
	public final void mDROP() throws RecognitionException
	{
		try
		{
			int _type = DROP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:270:6: ( D R O P )
			// Fatworm.g:270:8: D R O P
			{
				mD();
				mR();
				mO();
				mP();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DROP"
	// $ANTLR start "USE"
	public final void mUSE() throws RecognitionException
	{
		try
		{
			int _type = USE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:271:5: ( U S E )
			// Fatworm.g:271:7: U S E
			{
				mU();
				mS();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "USE"
	// $ANTLR start "INDEX"
	public final void mINDEX() throws RecognitionException
	{
		try
		{
			int _type = INDEX;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:272:7: ( I N D E X )
			// Fatworm.g:272:9: I N D E X
			{
				mI();
				mN();
				mD();
				mE();
				mX();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "INDEX"
	// $ANTLR start "TABLE"
	public final void mTABLE() throws RecognitionException
	{
		try
		{
			int _type = TABLE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:273:7: ( T A B L E )
			// Fatworm.g:273:9: T A B L E
			{
				mT();
				mA();
				mB();
				mL();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "TABLE"
	// $ANTLR start "PRIMARY"
	public final void mPRIMARY() throws RecognitionException
	{
		try
		{
			int _type = PRIMARY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:274:9: ( P R I M A R Y )
			// Fatworm.g:274:11: P R I M A R Y
			{
				mP();
				mR();
				mI();
				mM();
				mA();
				mR();
				mY();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "PRIMARY"
	// $ANTLR start "KEY"
	public final void mKEY() throws RecognitionException
	{
		try
		{
			int _type = KEY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:275:5: ( K E Y )
			// Fatworm.g:275:7: K E Y
			{
				mK();
				mE();
				mY();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "KEY"
	// $ANTLR start "NOT"
	public final void mNOT() throws RecognitionException
	{
		try
		{
			int _type = NOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:276:5: ( N O T )
			// Fatworm.g:276:7: N O T
			{
				mN();
				mO();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "NOT"
	// $ANTLR start "NULL"
	public final void mNULL() throws RecognitionException
	{
		try
		{
			int _type = NULL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:277:6: ( N U L L )
			// Fatworm.g:277:8: N U L L
			{
				mN();
				mU();
				mL();
				mL();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "NULL"
	// $ANTLR start "DEFAULT"
	public final void mDEFAULT() throws RecognitionException
	{
		try
		{
			int _type = DEFAULT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:278:9: ( D E F A U L T )
			// Fatworm.g:278:11: D E F A U L T
			{
				mD();
				mE();
				mF();
				mA();
				mU();
				mL();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DEFAULT"
	// $ANTLR start "AUTO_INCREMENT"
	public final void mAUTO_INCREMENT() throws RecognitionException
	{
		try
		{
			int _type = AUTO_INCREMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:279:16: ( A U T O '_' I N C R E M E N T )
			// Fatworm.g:279:18: A U T O '_' I N C R E M E N T
			{
				mA();
				mU();
				mT();
				mO();
				match('_');
				mI();
				mN();
				mC();
				mR();
				mE();
				mM();
				mE();
				mN();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "AUTO_INCREMENT"
	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException
	{
		try
		{
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:280:5: ( I N T )
			// Fatworm.g:280:7: I N T
			{
				mI();
				mN();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "INT"
	// $ANTLR start "FLOAT"
	public final void mFLOAT() throws RecognitionException
	{
		try
		{
			int _type = FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:281:7: ( F L O A T )
			// Fatworm.g:281:9: F L O A T
			{
				mF();
				mL();
				mO();
				mA();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "FLOAT"
	// $ANTLR start "CHAR"
	public final void mCHAR() throws RecognitionException
	{
		try
		{
			int _type = CHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:282:6: ( C H A R )
			// Fatworm.g:282:8: C H A R
			{
				mC();
				mH();
				mA();
				mR();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "CHAR"
	// $ANTLR start "DATETIME"
	public final void mDATETIME() throws RecognitionException
	{
		try
		{
			int _type = DATETIME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:283:10: ( D A T E T I M E )
			// Fatworm.g:283:12: D A T E T I M E
			{
				mD();
				mA();
				mT();
				mE();
				mT();
				mI();
				mM();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DATETIME"
	// $ANTLR start "BOOLEAN"
	public final void mBOOLEAN() throws RecognitionException
	{
		try
		{
			int _type = BOOLEAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:284:9: ( B O O L E A N )
			// Fatworm.g:284:11: B O O L E A N
			{
				mB();
				mO();
				mO();
				mL();
				mE();
				mA();
				mN();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "BOOLEAN"
	// $ANTLR start "DECIMAL"
	public final void mDECIMAL() throws RecognitionException
	{
		try
		{
			int _type = DECIMAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:285:9: ( D E C I M A L )
			// Fatworm.g:285:11: D E C I M A L
			{
				mD();
				mE();
				mC();
				mI();
				mM();
				mA();
				mL();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DECIMAL"
	// $ANTLR start "TIMESTAMP"
	public final void mTIMESTAMP() throws RecognitionException
	{
		try
		{
			int _type = TIMESTAMP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:286:11: ( T I M E S T A M P )
			// Fatworm.g:286:13: T I M E S T A M P
			{
				mT();
				mI();
				mM();
				mE();
				mS();
				mT();
				mA();
				mM();
				mP();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "TIMESTAMP"
	// $ANTLR start "VARCHAR"
	public final void mVARCHAR() throws RecognitionException
	{
		try
		{
			int _type = VARCHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:287:9: ( V A R C H A R )
			// Fatworm.g:287:11: V A R C H A R
			{
				mV();
				mA();
				mR();
				mC();
				mH();
				mA();
				mR();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "VARCHAR"
	// $ANTLR start "INSERT"
	public final void mINSERT() throws RecognitionException
	{
		try
		{
			int _type = INSERT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:288:8: ( I N S E R T )
			// Fatworm.g:288:10: I N S E R T
			{
				mI();
				mN();
				mS();
				mE();
				mR();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "INSERT"
	// $ANTLR start "INTO"
	public final void mINTO() throws RecognitionException
	{
		try
		{
			int _type = INTO;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:289:6: ( I N T O )
			// Fatworm.g:289:8: I N T O
			{
				mI();
				mN();
				mT();
				mO();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "INTO"
	// $ANTLR start "VALUES"
	public final void mVALUES() throws RecognitionException
	{
		try
		{
			int _type = VALUES;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:290:8: ( V A L U E S )
			// Fatworm.g:290:10: V A L U E S
			{
				mV();
				mA();
				mL();
				mU();
				mE();
				mS();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "VALUES"
	// $ANTLR start "DELETE"
	public final void mDELETE() throws RecognitionException
	{
		try
		{
			int _type = DELETE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:291:8: ( D E L E T E )
			// Fatworm.g:291:10: D E L E T E
			{
				mD();
				mE();
				mL();
				mE();
				mT();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DELETE"
	// $ANTLR start "FROM"
	public final void mFROM() throws RecognitionException
	{
		try
		{
			int _type = FROM;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:292:6: ( F R O M )
			// Fatworm.g:292:8: F R O M
			{
				mF();
				mR();
				mO();
				mM();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "FROM"
	// $ANTLR start "WHERE"
	public final void mWHERE() throws RecognitionException
	{
		try
		{
			int _type = WHERE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:293:7: ( W H E R E )
			// Fatworm.g:293:9: W H E R E
			{
				mW();
				mH();
				mE();
				mR();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "WHERE"
	// $ANTLR start "UPDATE"
	public final void mUPDATE() throws RecognitionException
	{
		try
		{
			int _type = UPDATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:294:8: ( U P D A T E )
			// Fatworm.g:294:10: U P D A T E
			{
				mU();
				mP();
				mD();
				mA();
				mT();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "UPDATE"
	// $ANTLR start "SET"
	public final void mSET() throws RecognitionException
	{
		try
		{
			int _type = SET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:295:5: ( S E T )
			// Fatworm.g:295:7: S E T
			{
				mS();
				mE();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "SET"
	// $ANTLR start "UNIQUE"
	public final void mUNIQUE() throws RecognitionException
	{
		try
		{
			int _type = UNIQUE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:296:8: ( U N I Q U E )
			// Fatworm.g:296:10: U N I Q U E
			{
				mU();
				mN();
				mI();
				mQ();
				mU();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "UNIQUE"
	// $ANTLR start "ON"
	public final void mON() throws RecognitionException
	{
		try
		{
			int _type = ON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:297:4: ( O N )
			// Fatworm.g:297:6: O N
			{
				mO();
				mN();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "ON"
	// $ANTLR start "DISTINCT"
	public final void mDISTINCT() throws RecognitionException
	{
		try
		{
			int _type = DISTINCT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:298:10: ( D I S T I N C T )
			// Fatworm.g:298:12: D I S T I N C T
			{
				mD();
				mI();
				mS();
				mT();
				mI();
				mN();
				mC();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DISTINCT"
	// $ANTLR start "GROUP"
	public final void mGROUP() throws RecognitionException
	{
		try
		{
			int _type = GROUP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:299:7: ( G R O U P )
			// Fatworm.g:299:9: G R O U P
			{
				mG();
				mR();
				mO();
				mU();
				mP();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "GROUP"
	// $ANTLR start "BY"
	public final void mBY() throws RecognitionException
	{
		try
		{
			int _type = BY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:300:4: ( B Y )
			// Fatworm.g:300:6: B Y
			{
				mB();
				mY();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "BY"
	// $ANTLR start "HAVING"
	public final void mHAVING() throws RecognitionException
	{
		try
		{
			int _type = HAVING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:301:8: ( H A V I N G )
			// Fatworm.g:301:10: H A V I N G
			{
				mH();
				mA();
				mV();
				mI();
				mN();
				mG();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "HAVING"
	// $ANTLR start "ORDER"
	public final void mORDER() throws RecognitionException
	{
		try
		{
			int _type = ORDER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:302:7: ( O R D E R )
			// Fatworm.g:302:9: O R D E R
			{
				mO();
				mR();
				mD();
				mE();
				mR();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "ORDER"
	// $ANTLR start "ASC"
	public final void mASC() throws RecognitionException
	{
		try
		{
			int _type = ASC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:303:5: ( A S C )
			// Fatworm.g:303:7: A S C
			{
				mA();
				mS();
				mC();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "ASC"
	// $ANTLR start "DESC"
	public final void mDESC() throws RecognitionException
	{
		try
		{
			int _type = DESC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:304:6: ( D E S C )
			// Fatworm.g:304:8: D E S C
			{
				mD();
				mE();
				mS();
				mC();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DESC"
	// $ANTLR start "AS"
	public final void mAS() throws RecognitionException
	{
		try
		{
			int _type = AS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:305:4: ( A S )
			// Fatworm.g:305:6: A S
			{
				mA();
				mS();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "AS"
	// $ANTLR start "AVG"
	public final void mAVG() throws RecognitionException
	{
		try
		{
			int _type = AVG;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:306:5: ( A V G )
			// Fatworm.g:306:7: A V G
			{
				mA();
				mV();
				mG();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "AVG"
	// $ANTLR start "COUNT"
	public final void mCOUNT() throws RecognitionException
	{
		try
		{
			int _type = COUNT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:307:7: ( C O U N T )
			// Fatworm.g:307:9: C O U N T
			{
				mC();
				mO();
				mU();
				mN();
				mT();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "COUNT"
	// $ANTLR start "MIN"
	public final void mMIN() throws RecognitionException
	{
		try
		{
			int _type = MIN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:308:5: ( M I N )
			// Fatworm.g:308:7: M I N
			{
				mM();
				mI();
				mN();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "MIN"
	// $ANTLR start "MAX"
	public final void mMAX() throws RecognitionException
	{
		try
		{
			int _type = MAX;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:309:5: ( M A X )
			// Fatworm.g:309:7: M A X
			{
				mM();
				mA();
				mX();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "MAX"
	// $ANTLR start "SUM"
	public final void mSUM() throws RecognitionException
	{
		try
		{
			int _type = SUM;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:310:5: ( S U M )
			// Fatworm.g:310:7: S U M
			{
				mS();
				mU();
				mM();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "SUM"
	// $ANTLR start "AND"
	public final void mAND() throws RecognitionException
	{
		try
		{
			int _type = AND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:311:5: ( A N D )
			// Fatworm.g:311:7: A N D
			{
				mA();
				mN();
				mD();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "AND"
	// $ANTLR start "OR"
	public final void mOR() throws RecognitionException
	{
		try
		{
			int _type = OR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:312:4: ( O R )
			// Fatworm.g:312:6: O R
			{
				mO();
				mR();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "OR"
	// $ANTLR start "EXISTS"
	public final void mEXISTS() throws RecognitionException
	{
		try
		{
			int _type = EXISTS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:313:8: ( E X I S T S )
			// Fatworm.g:313:10: E X I S T S
			{
				mE();
				mX();
				mI();
				mS();
				mT();
				mS();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "EXISTS"
	// $ANTLR start "ANY"
	public final void mANY() throws RecognitionException
	{
		try
		{
			int _type = ANY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:314:5: ( A N Y )
			// Fatworm.g:314:7: A N Y
			{
				mA();
				mN();
				mY();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "ANY"
	// $ANTLR start "IN"
	public final void mIN() throws RecognitionException
	{
		try
		{
			int _type = IN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:315:4: ( I N )
			// Fatworm.g:315:6: I N
			{
				mI();
				mN();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "IN"
	// $ANTLR start "ALL"
	public final void mALL() throws RecognitionException
	{
		try
		{
			int _type = ALL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:316:5: ( A L L )
			// Fatworm.g:316:7: A L L
			{
				mA();
				mL();
				mL();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "ALL"
	// $ANTLR start "TRUE"
	public final void mTRUE() throws RecognitionException
	{
		try
		{
			int _type = TRUE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:317:6: ( T R U E )
			// Fatworm.g:317:8: T R U E
			{
				mT();
				mR();
				mU();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "TRUE"
	// $ANTLR start "FALSE"
	public final void mFALSE() throws RecognitionException
	{
		try
		{
			int _type = FALSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:318:7: ( F A L S E )
			// Fatworm.g:318:9: F A L S E
			{
				mF();
				mA();
				mL();
				mS();
				mE();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "FALSE"
	// $ANTLR start "ID"
	public final void mID() throws RecognitionException
	{
		try
		{
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:320:3: ( LETTER ( LETTER | '0' .. '9' )* )
			// Fatworm.g:320:5: LETTER ( LETTER | '0' .. '9' )*
			{
				mLETTER();
				// Fatworm.g:320:12: ( LETTER | '0' .. '9' )*
				loop1: while (true)
				{
					int alt1 = 2;
					int LA1_0 = input.LA(1);
					if (((LA1_0 >= '0' && LA1_0 <= '9') || (LA1_0 >= 'A' && LA1_0 <= 'Z') || LA1_0 == '_' || (LA1_0 >= 'a' && LA1_0 <= 'z')))
					{
						alt1 = 1;
					}
					switch (alt1)
					{
					case 1:
					// Fatworm.g:
					{
						if ((input.LA(1) >= '0' && input.LA(1) <= '9') || (input.LA(1) >= 'A' && input.LA(1) <= 'Z') || input.LA(1) == '_' || (input.LA(1) >= 'a' && input.LA(1) <= 'z'))
						{
							input.consume();
						}
						else
						{
							MismatchedSetException mse = new MismatchedSetException(null, input);
							recover(mse);
							throw mse;
						}
					}
						break;
					default:
						break loop1;
					}
				}
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "ID"
	// $ANTLR start "STRING_LITERAL"
	public final void mSTRING_LITERAL() throws RecognitionException
	{
		try
		{
			int _type = STRING_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:324:3: ( '\\'' (~ ( '\\\\' | '\\'' ) )* '\\'' )
			// Fatworm.g:324:5: '\\'' (~ ( '\\\\' | '\\'' ) )* '\\''
			{
				match('\'');
				// Fatworm.g:324:10: (~ ( '\\\\' | '\\'' ) )*
				loop2: while (true)
				{
					int alt2 = 2;
					int LA2_0 = input.LA(1);
					if (((LA2_0 >= '\u0000' && LA2_0 <= '&') || (LA2_0 >= '(' && LA2_0 <= '[') || (LA2_0 >= ']' && LA2_0 <= '\uFFFF')))
					{
						alt2 = 1;
					}
					switch (alt2)
					{
					case 1:
					// Fatworm.g:
					{
						if ((input.LA(1) >= '\u0000' && input.LA(1) <= '&') || (input.LA(1) >= '(' && input.LA(1) <= '[') || (input.LA(1) >= ']' && input.LA(1) <= '\uFFFF'))
						{
							input.consume();
						}
						else
						{
							MismatchedSetException mse = new MismatchedSetException(null, input);
							recover(mse);
							throw mse;
						}
					}
						break;
					default:
						break loop2;
					}
				}
				match('\'');
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "STRING_LITERAL"
	// $ANTLR start "INTEGER_LITERAL"
	public final void mINTEGER_LITERAL() throws RecognitionException
	{
		try
		{
			int _type = INTEGER_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:328:3: ( ( DIGIT )+ )
			// Fatworm.g:328:5: ( DIGIT )+
			{
				// Fatworm.g:328:5: ( DIGIT )+
				int cnt3 = 0;
				loop3: while (true)
				{
					int alt3 = 2;
					int LA3_0 = input.LA(1);
					if (((LA3_0 >= '0' && LA3_0 <= '9')))
					{
						alt3 = 1;
					}
					switch (alt3)
					{
					case 1:
					// Fatworm.g:
					{
						if ((input.LA(1) >= '0' && input.LA(1) <= '9'))
						{
							input.consume();
						}
						else
						{
							MismatchedSetException mse = new MismatchedSetException(null, input);
							recover(mse);
							throw mse;
						}
					}
						break;
					default:
						if (cnt3 >= 1) break loop3;
						EarlyExitException eee = new EarlyExitException(3, input);
						throw eee;
					}
					cnt3++;
				}
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "INTEGER_LITERAL"
	// $ANTLR start "FLOAT_LITERAL"
	public final void mFLOAT_LITERAL() throws RecognitionException
	{
		try
		{
			int _type = FLOAT_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:332:3: ( INTEGER_LITERAL '.' INTEGER_LITERAL )
			// Fatworm.g:332:5: INTEGER_LITERAL '.' INTEGER_LITERAL
			{
				mINTEGER_LITERAL();
				match('.');
				mINTEGER_LITERAL();
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "FLOAT_LITERAL"
	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException
	{
		try
		{
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Fatworm.g:335:3: ( ( ' ' | '\\r' | '\\t' | '\ ' | '\\n' ) )
			// Fatworm.g:335:5: ( ' ' | '\\r' | '\\t' | '\ ' | '\\n' )
			{
				if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || (input.LA(1) >= '\f' && input.LA(1) <= '\r') || input.LA(1) == ' ')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
				_channel = HIDDEN;
			}
			state.type = _type;
			state.channel = _channel;
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "WS"
	// $ANTLR start "LETTER"
	public final void mLETTER() throws RecognitionException
	{
		try
		{
			// Fatworm.g:341:3: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
			// Fatworm.g:
			{
				if ((input.LA(1) >= 'A' && input.LA(1) <= 'Z') || input.LA(1) == '_' || (input.LA(1) >= 'a' && input.LA(1) <= 'z'))
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "LETTER"
	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException
	{
		try
		{
			// Fatworm.g:348:3: ( '0' .. '9' )
			// Fatworm.g:
			{
				if ((input.LA(1) >= '0' && input.LA(1) <= '9'))
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "DIGIT"
	// $ANTLR start "A"
	public final void mA() throws RecognitionException
	{
		try
		{
			// Fatworm.g:350:12: ( 'a' | 'A' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'A' || input.LA(1) == 'a')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "A"
	// $ANTLR start "B"
	public final void mB() throws RecognitionException
	{
		try
		{
			// Fatworm.g:351:12: ( 'b' | 'B' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'B' || input.LA(1) == 'b')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "B"
	// $ANTLR start "C"
	public final void mC() throws RecognitionException
	{
		try
		{
			// Fatworm.g:352:12: ( 'c' | 'C' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'C' || input.LA(1) == 'c')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "C"
	// $ANTLR start "D"
	public final void mD() throws RecognitionException
	{
		try
		{
			// Fatworm.g:353:12: ( 'd' | 'D' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'D' || input.LA(1) == 'd')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "D"
	// $ANTLR start "E"
	public final void mE() throws RecognitionException
	{
		try
		{
			// Fatworm.g:354:12: ( 'e' | 'E' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'E' || input.LA(1) == 'e')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "E"
	// $ANTLR start "F"
	public final void mF() throws RecognitionException
	{
		try
		{
			// Fatworm.g:355:12: ( 'f' | 'F' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'F' || input.LA(1) == 'f')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "F"
	// $ANTLR start "G"
	public final void mG() throws RecognitionException
	{
		try
		{
			// Fatworm.g:356:12: ( 'g' | 'G' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'G' || input.LA(1) == 'g')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "G"
	// $ANTLR start "H"
	public final void mH() throws RecognitionException
	{
		try
		{
			// Fatworm.g:357:12: ( 'h' | 'H' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'H' || input.LA(1) == 'h')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "H"
	// $ANTLR start "I"
	public final void mI() throws RecognitionException
	{
		try
		{
			// Fatworm.g:358:12: ( 'i' | 'I' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'I' || input.LA(1) == 'i')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "I"
	// $ANTLR start "J"
	public final void mJ() throws RecognitionException
	{
		try
		{
			// Fatworm.g:359:12: ( 'j' | 'J' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'J' || input.LA(1) == 'j')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "J"
	// $ANTLR start "K"
	public final void mK() throws RecognitionException
	{
		try
		{
			// Fatworm.g:360:12: ( 'k' | 'K' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'K' || input.LA(1) == 'k')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "K"
	// $ANTLR start "L"
	public final void mL() throws RecognitionException
	{
		try
		{
			// Fatworm.g:361:12: ( 'l' | 'L' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'L' || input.LA(1) == 'l')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "L"
	// $ANTLR start "M"
	public final void mM() throws RecognitionException
	{
		try
		{
			// Fatworm.g:362:12: ( 'm' | 'M' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'M' || input.LA(1) == 'm')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "M"
	// $ANTLR start "N"
	public final void mN() throws RecognitionException
	{
		try
		{
			// Fatworm.g:363:12: ( 'n' | 'N' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'N' || input.LA(1) == 'n')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "N"
	// $ANTLR start "O"
	public final void mO() throws RecognitionException
	{
		try
		{
			// Fatworm.g:364:12: ( 'o' | 'O' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'O' || input.LA(1) == 'o')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "O"
	// $ANTLR start "P"
	public final void mP() throws RecognitionException
	{
		try
		{
			// Fatworm.g:365:12: ( 'p' | 'P' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'P' || input.LA(1) == 'p')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "P"
	// $ANTLR start "Q"
	public final void mQ() throws RecognitionException
	{
		try
		{
			// Fatworm.g:366:12: ( 'q' | 'Q' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'Q' || input.LA(1) == 'q')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "Q"
	// $ANTLR start "R"
	public final void mR() throws RecognitionException
	{
		try
		{
			// Fatworm.g:367:12: ( 'r' | 'R' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'R' || input.LA(1) == 'r')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "R"
	// $ANTLR start "S"
	public final void mS() throws RecognitionException
	{
		try
		{
			// Fatworm.g:368:12: ( 's' | 'S' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'S' || input.LA(1) == 's')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "S"
	// $ANTLR start "T"
	public final void mT() throws RecognitionException
	{
		try
		{
			// Fatworm.g:369:12: ( 't' | 'T' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'T' || input.LA(1) == 't')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "T"
	// $ANTLR start "U"
	public final void mU() throws RecognitionException
	{
		try
		{
			// Fatworm.g:370:12: ( 'u' | 'U' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'U' || input.LA(1) == 'u')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "U"
	// $ANTLR start "V"
	public final void mV() throws RecognitionException
	{
		try
		{
			// Fatworm.g:371:12: ( 'v' | 'V' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'V' || input.LA(1) == 'v')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "V"
	// $ANTLR start "W"
	public final void mW() throws RecognitionException
	{
		try
		{
			// Fatworm.g:372:12: ( 'w' | 'W' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'W' || input.LA(1) == 'w')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "W"
	// $ANTLR start "X"
	public final void mX() throws RecognitionException
	{
		try
		{
			// Fatworm.g:373:12: ( 'x' | 'X' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'X' || input.LA(1) == 'x')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "X"
	// $ANTLR start "Y"
	public final void mY() throws RecognitionException
	{
		try
		{
			// Fatworm.g:374:12: ( 'y' | 'Y' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'Y' || input.LA(1) == 'y')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "Y"
	// $ANTLR start "Z"
	public final void mZ() throws RecognitionException
	{
		try
		{
			// Fatworm.g:375:12: ( 'z' | 'Z' )
			// Fatworm.g:
			{
				if (input.LA(1) == 'Z' || input.LA(1) == 'z')
				{
					input.consume();
				}
				else
				{
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}
			}
		} finally
		{
			// do for sure before leaving
		}
	}

	// $ANTLR end "Z"
	@Override
	public void mTokens() throws RecognitionException
	{
		// Fatworm.g:1:8: ( T__105 | T__106 | T__107 | T__108 | T__109 | T__110
		// | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 |
		// T__118 | T__119 | SELECT | CREATE | DATABASE | DROP | USE | INDEX |
		// TABLE | PRIMARY | KEY | NOT | NULL | DEFAULT | AUTO_INCREMENT | INT |
		// FLOAT | CHAR | DATETIME | BOOLEAN | DECIMAL | TIMESTAMP | VARCHAR |
		// INSERT | INTO | VALUES | DELETE | FROM | WHERE | UPDATE | SET |
		// UNIQUE | ON | DISTINCT | GROUP | BY | HAVING | ORDER | ASC | DESC |
		// AS | AVG | COUNT | MIN | MAX | SUM | AND | OR | EXISTS | ANY | IN |
		// ALL | TRUE | FALSE | ID | STRING_LITERAL | INTEGER_LITERAL |
		// FLOAT_LITERAL | WS )
		int alt4 = 72;
		alt4 = dfa4.predict(input);
		switch (alt4)
		{
		case 1:
		// Fatworm.g:1:10: T__105
		{
			mT__105();
		}
			break;
		case 2:
		// Fatworm.g:1:17: T__106
		{
			mT__106();
		}
			break;
		case 3:
		// Fatworm.g:1:24: T__107
		{
			mT__107();
		}
			break;
		case 4:
		// Fatworm.g:1:31: T__108
		{
			mT__108();
		}
			break;
		case 5:
		// Fatworm.g:1:38: T__109
		{
			mT__109();
		}
			break;
		case 6:
		// Fatworm.g:1:45: T__110
		{
			mT__110();
		}
			break;
		case 7:
		// Fatworm.g:1:52: T__111
		{
			mT__111();
		}
			break;
		case 8:
		// Fatworm.g:1:59: T__112
		{
			mT__112();
		}
			break;
		case 9:
		// Fatworm.g:1:66: T__113
		{
			mT__113();
		}
			break;
		case 10:
		// Fatworm.g:1:73: T__114
		{
			mT__114();
		}
			break;
		case 11:
		// Fatworm.g:1:80: T__115
		{
			mT__115();
		}
			break;
		case 12:
		// Fatworm.g:1:87: T__116
		{
			mT__116();
		}
			break;
		case 13:
		// Fatworm.g:1:94: T__117
		{
			mT__117();
		}
			break;
		case 14:
		// Fatworm.g:1:101: T__118
		{
			mT__118();
		}
			break;
		case 15:
		// Fatworm.g:1:108: T__119
		{
			mT__119();
		}
			break;
		case 16:
		// Fatworm.g:1:115: SELECT
		{
			mSELECT();
		}
			break;
		case 17:
		// Fatworm.g:1:122: CREATE
		{
			mCREATE();
		}
			break;
		case 18:
		// Fatworm.g:1:129: DATABASE
		{
			mDATABASE();
		}
			break;
		case 19:
		// Fatworm.g:1:138: DROP
		{
			mDROP();
		}
			break;
		case 20:
		// Fatworm.g:1:143: USE
		{
			mUSE();
		}
			break;
		case 21:
		// Fatworm.g:1:147: INDEX
		{
			mINDEX();
		}
			break;
		case 22:
		// Fatworm.g:1:153: TABLE
		{
			mTABLE();
		}
			break;
		case 23:
		// Fatworm.g:1:159: PRIMARY
		{
			mPRIMARY();
		}
			break;
		case 24:
		// Fatworm.g:1:167: KEY
		{
			mKEY();
		}
			break;
		case 25:
		// Fatworm.g:1:171: NOT
		{
			mNOT();
		}
			break;
		case 26:
		// Fatworm.g:1:175: NULL
		{
			mNULL();
		}
			break;
		case 27:
		// Fatworm.g:1:180: DEFAULT
		{
			mDEFAULT();
		}
			break;
		case 28:
		// Fatworm.g:1:188: AUTO_INCREMENT
		{
			mAUTO_INCREMENT();
		}
			break;
		case 29:
		// Fatworm.g:1:203: INT
		{
			mINT();
		}
			break;
		case 30:
		// Fatworm.g:1:207: FLOAT
		{
			mFLOAT();
		}
			break;
		case 31:
		// Fatworm.g:1:213: CHAR
		{
			mCHAR();
		}
			break;
		case 32:
		// Fatworm.g:1:218: DATETIME
		{
			mDATETIME();
		}
			break;
		case 33:
		// Fatworm.g:1:227: BOOLEAN
		{
			mBOOLEAN();
		}
			break;
		case 34:
		// Fatworm.g:1:235: DECIMAL
		{
			mDECIMAL();
		}
			break;
		case 35:
		// Fatworm.g:1:243: TIMESTAMP
		{
			mTIMESTAMP();
		}
			break;
		case 36:
		// Fatworm.g:1:253: VARCHAR
		{
			mVARCHAR();
		}
			break;
		case 37:
		// Fatworm.g:1:261: INSERT
		{
			mINSERT();
		}
			break;
		case 38:
		// Fatworm.g:1:268: INTO
		{
			mINTO();
		}
			break;
		case 39:
		// Fatworm.g:1:273: VALUES
		{
			mVALUES();
		}
			break;
		case 40:
		// Fatworm.g:1:280: DELETE
		{
			mDELETE();
		}
			break;
		case 41:
		// Fatworm.g:1:287: FROM
		{
			mFROM();
		}
			break;
		case 42:
		// Fatworm.g:1:292: WHERE
		{
			mWHERE();
		}
			break;
		case 43:
		// Fatworm.g:1:298: UPDATE
		{
			mUPDATE();
		}
			break;
		case 44:
		// Fatworm.g:1:305: SET
		{
			mSET();
		}
			break;
		case 45:
		// Fatworm.g:1:309: UNIQUE
		{
			mUNIQUE();
		}
			break;
		case 46:
		// Fatworm.g:1:316: ON
		{
			mON();
		}
			break;
		case 47:
		// Fatworm.g:1:319: DISTINCT
		{
			mDISTINCT();
		}
			break;
		case 48:
		// Fatworm.g:1:328: GROUP
		{
			mGROUP();
		}
			break;
		case 49:
		// Fatworm.g:1:334: BY
		{
			mBY();
		}
			break;
		case 50:
		// Fatworm.g:1:337: HAVING
		{
			mHAVING();
		}
			break;
		case 51:
		// Fatworm.g:1:344: ORDER
		{
			mORDER();
		}
			break;
		case 52:
		// Fatworm.g:1:350: ASC
		{
			mASC();
		}
			break;
		case 53:
		// Fatworm.g:1:354: DESC
		{
			mDESC();
		}
			break;
		case 54:
		// Fatworm.g:1:359: AS
		{
			mAS();
		}
			break;
		case 55:
		// Fatworm.g:1:362: AVG
		{
			mAVG();
		}
			break;
		case 56:
		// Fatworm.g:1:366: COUNT
		{
			mCOUNT();
		}
			break;
		case 57:
		// Fatworm.g:1:372: MIN
		{
			mMIN();
		}
			break;
		case 58:
		// Fatworm.g:1:376: MAX
		{
			mMAX();
		}
			break;
		case 59:
		// Fatworm.g:1:380: SUM
		{
			mSUM();
		}
			break;
		case 60:
		// Fatworm.g:1:384: AND
		{
			mAND();
		}
			break;
		case 61:
		// Fatworm.g:1:388: OR
		{
			mOR();
		}
			break;
		case 62:
		// Fatworm.g:1:391: EXISTS
		{
			mEXISTS();
		}
			break;
		case 63:
		// Fatworm.g:1:398: ANY
		{
			mANY();
		}
			break;
		case 64:
		// Fatworm.g:1:402: IN
		{
			mIN();
		}
			break;
		case 65:
		// Fatworm.g:1:405: ALL
		{
			mALL();
		}
			break;
		case 66:
		// Fatworm.g:1:409: TRUE
		{
			mTRUE();
		}
			break;
		case 67:
		// Fatworm.g:1:414: FALSE
		{
			mFALSE();
		}
			break;
		case 68:
		// Fatworm.g:1:420: ID
		{
			mID();
		}
			break;
		case 69:
		// Fatworm.g:1:423: STRING_LITERAL
		{
			mSTRING_LITERAL();
		}
			break;
		case 70:
		// Fatworm.g:1:438: INTEGER_LITERAL
		{
			mINTEGER_LITERAL();
		}
			break;
		case 71:
		// Fatworm.g:1:454: FLOAT_LITERAL
		{
			mFLOAT_LITERAL();
		}
			break;
		case 72:
		// Fatworm.g:1:468: WS
		{
			mWS();
		}
			break;
		}
	}

	protected DFA4 dfa4 = new DFA4(this);

	static final String DFA4_eotS = "\12\uffff\1\46\1\uffff\1\50\23\40\2\uffff\1\120\6\uffff\14\40\1\142\10" + "\40\1\156\7\40\1\170\2\40\1\174\1\175\5\40\2\uffff\1\40\1\u0085\1\u0086" + "\12\40\1\u0092\2\40\1\uffff\1\40\1\u0096\5\40\1\u009d\1\u009e\2\40\1\uffff" + "\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\4\40\1\uffff\3\40\2\uffff\3\40" + "\1\u00b0\1\u00b1\2\40\2\uffff\1\40\1\u00b5\3\40\1\u00b9\3\40\1\u00bd\1" + "\40\1\uffff\3\40\1\uffff\1\u00c2\3\40\1\u00c6\1\40\2\uffff\1\u00c8\1\40" + "\5\uffff\1\40\1\u00cb\10\40\2\uffff\3\40\1\uffff\1\u00d7\2\40\1\uffff" + "\3\40\1\uffff\3\40\1\u00e0\1\uffff\1\40\1\u00e2\1\40\1\uffff\1\40\1\uffff" + "\1\40\1\u00e6\1\uffff\1\u00e7\3\40\1\u00eb\1\u00ec\1\u00ed\2\40\1\u00f0" + "\1\u00f1\1\uffff\4\40\1\u00f6\1\40\1\u00f8\1\u00f9\1\uffff\1\u00fa\1\uffff"
			+ "\3\40\2\uffff\2\40\1\u0100\3\uffff\1\u0101\1\u0102\2\uffff\2\40\1\u0105" + "\1\u0106\1\uffff\1\40\3\uffff\1\40\1\u0109\1\40\1\u010b\1\u010c\3\uffff" + "\1\u010d\1\u010e\2\uffff\1\u010f\1\40\1\uffff\1\40\5\uffff\1\u0112\1\40" + "\1\uffff\4\40\1\u0118\1\uffff";

	static final String DFA4_eofS = "\u0119\uffff";

	static final String DFA4_minS = "\1\11\11\uffff\1\75\1\uffff\1\75\1\105\1\110\1\101\2\116\1\101\1\122\1" + "\105\1\117\1\114\1\101\1\117\1\101\1\110\1\116\1\122\2\101\1\130\2\uffff" + "\1\56\6\uffff\1\114\1\115\1\105\1\101\1\125\1\124\1\117\1\103\1\123\1" + "\105\1\104\1\111\1\60\1\102\1\115\1\125\1\111\1\131\1\124\1\114\1\124" + "\1\60\1\107\1\104\1\114\2\117\1\114\1\117\1\60\1\114\1\105\2\60\1\117" + "\1\126\1\116\1\130\1\111\2\uffff\1\105\2\60\1\101\1\122\1\116\1\101\1" + "\120\1\101\1\111\1\105\1\103\1\124\1\60\1\101\1\121\1\uffff\1\105\1\60" + "\1\105\1\114\2\105\1\115\2\60\1\114\1\117\1\uffff\5\60\1\101\1\115\1\123" + "\1\114\1\uffff\1\103\1\125\1\122\2\uffff\1\105\1\125\1\111\2\60\1\123" + "\1\103\2\uffff\1\124\1\60\1\124\1\102\1\124\1\60\1\125\1\115\1\124\1\60"
			+ "\1\111\1\uffff\1\124\1\125\1\130\1\uffff\1\60\1\122\1\105\1\123\1\60\1" + "\101\2\uffff\1\60\1\137\5\uffff\1\124\1\60\2\105\1\110\2\105\1\122\1\120" + "\1\116\2\uffff\2\124\1\105\1\uffff\1\60\1\101\1\111\1\uffff\1\114\1\101" + "\1\105\1\uffff\1\116\2\105\1\60\1\uffff\1\124\1\60\1\124\1\uffff\1\122" + "\1\uffff\1\111\1\60\1\uffff\1\60\2\101\1\123\3\60\1\107\1\123\2\60\1\uffff" + "\1\123\1\115\1\124\1\114\1\60\1\103\2\60\1\uffff\1\60\1\uffff\1\101\1" + "\131\1\116\2\uffff\1\116\1\122\1\60\3\uffff\2\60\2\uffff\2\105\2\60\1" + "\uffff\1\124\3\uffff\1\115\1\60\1\103\2\60\3\uffff\2\60\2\uffff\1\60\1" + "\120\1\uffff\1\122\5\uffff\1\60\1\105\1\uffff\1\115\1\105\1\116\1\124" + "\1\60\1\uffff";

	static final String DFA4_maxS = "\1\172\11\uffff\1\76\1\uffff\1\75\1\165\2\162\1\163\1\156\2\162\1\145" + "\1\165\1\166\1\162\1\171\1\141\1\150\2\162\1\141\1\151\1\170\2\uffff\1" + "\71\6\uffff\1\164\1\155\1\145\1\141\1\165\1\164\1\157\2\163\1\145\1\144" + "\1\151\1\172\1\142\1\155\1\165\1\151\1\171\1\164\1\154\1\164\1\172\1\147" + "\1\171\1\154\2\157\1\154\1\157\1\172\1\162\1\145\2\172\1\157\1\166\1\156" + "\1\170\1\151\2\uffff\1\145\2\172\1\141\1\162\1\156\1\145\1\160\1\141\1" + "\151\1\145\1\143\1\164\1\172\1\141\1\161\1\uffff\1\145\1\172\1\145\1\154" + "\2\145\1\155\2\172\1\154\1\157\1\uffff\5\172\1\141\1\155\1\163\1\154\1" + "\uffff\1\143\1\165\1\162\2\uffff\1\145\1\165\1\151\2\172\1\163\1\143\2" + "\uffff\1\164\1\172\1\164\1\142\1\164\1\172\1\165\1\155\1\164\1\172\1\151"
			+ "\1\uffff\1\164\1\165\1\170\1\uffff\1\172\1\162\1\145\1\163\1\172\1\141" + "\2\uffff\1\172\1\137\5\uffff\1\164\1\172\2\145\1\150\2\145\1\162\1\160" + "\1\156\2\uffff\2\164\1\145\1\uffff\1\172\1\141\1\151\1\uffff\1\154\1\141" + "\1\145\1\uffff\1\156\2\145\1\172\1\uffff\1\164\1\172\1\164\1\uffff\1\162" + "\1\uffff\1\151\1\172\1\uffff\1\172\2\141\1\163\3\172\1\147\1\163\2\172" + "\1\uffff\1\163\1\155\1\164\1\154\1\172\1\143\2\172\1\uffff\1\172\1\uffff" + "\1\141\1\171\1\156\2\uffff\1\156\1\162\1\172\3\uffff\2\172\2\uffff\2\145" + "\2\172\1\uffff\1\164\3\uffff\1\155\1\172\1\143\2\172\3\uffff\2\172\2\uffff" + "\1\172\1\160\1\uffff\1\162\5\uffff\1\172\1\145\1\uffff\1\155\1\145\1\156" + "\1\164\1\172\1\uffff";

	static final String DFA4_acceptS = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\15\24\uffff\1" + "\104\1\105\1\uffff\1\110\1\13\1\14\1\12\1\17\1\16\47\uffff\1\106\1\107" + "\20\uffff\1\100\13\uffff\1\66\11\uffff\1\61\3\uffff\1\56\1\75\7\uffff" + "\1\54\1\73\13\uffff\1\24\3\uffff\1\35\6\uffff\1\30\1\31\2\uffff\1\64\1" + "\67\1\74\1\77\1\101\12\uffff\1\71\1\72\3\uffff\1\37\3\uffff\1\23\3\uffff" + "\1\65\4\uffff\1\46\3\uffff\1\102\1\uffff\1\32\2\uffff\1\51\13\uffff\1" + "\70\10\uffff\1\25\1\uffff\1\26\3\uffff\1\36\1\103\3\uffff\1\52\1\63\1" + "\60\2\uffff\1\20\1\21\4\uffff\1\50\1\uffff\1\53\1\55\1\45\5\uffff\1\47" + "\1\62\1\76\2\uffff\1\33\1\42\2\uffff\1\27\1\uffff\1\41\1\44\1\22\1\40" + "\1\57\2\uffff\1\43\5\uffff\1\34";

	static final String DFA4_specialS = "\u0119\uffff}>";

	static final String[] DFA4_transitionS =
	{ "\2\43\1\uffff\2\43\22\uffff\1\43\4\uffff\1\1\1\uffff\1\41\1\2\1\3\1\4" + "\1\5\1\6\1\7\1\10\1\11\12\42\2\uffff\1\12\1\13\1\14\2\uffff\1\26\1\30" + "\1\16\1\17\1\37\1\27\1\34\1\35\1\21\1\40\1\24\1\40\1\36\1\25\1\33\1\23" + "\2\40\1\15\1\22\1\20\1\31\1\32\3\40\4\uffff\1\40\1\uffff\1\26\1\30\1" + "\16\1\17\1\37\1\27\1\34\1\35\1\21\1\40\1\24\1\40\1\36\1\25\1\33\1\23" + "\2\40\1\15\1\22\1\20\1\31\1\32\3\40", "", "", "", "", "", "", "", "", "", "\1\44\1\45", "", "\1\47", "\1\51\17\uffff\1\52\17\uffff\1\51\17\uffff\1\52", "\1\54\6\uffff\1\55\2\uffff\1\53\25\uffff\1\54\6\uffff\1\55\2\uffff\1" + "\53", "\1\56\3\uffff\1\60\3\uffff\1\61\10\uffff\1\57\16\uffff\1\56\3\uffff" + "\1\60\3\uffff\1\61\10\uffff\1\57", "\1\64\1\uffff\1\63\2\uffff\1\62\32\uffff\1\64\1\uffff\1\63\2\uffff\1" + "\62",
			"\1\65\37\uffff\1\65", "\1\66\7\uffff\1\67\10\uffff\1\70\16\uffff\1\66\7\uffff\1\67\10\uffff" + "\1\70", "\1\71\37\uffff\1\71", "\1\72\37\uffff\1\72", "\1\73\5\uffff\1\74\31\uffff\1\73\5\uffff\1\74", "\1\101\1\uffff\1\100\4\uffff\1\76\1\uffff\1\75\1\77\25\uffff\1\101\1" + "\uffff\1\100\4\uffff\1\76\1\uffff\1\75\1\77", "\1\104\12\uffff\1\102\5\uffff\1\103\16\uffff\1\104\12\uffff\1\102\5" + "\uffff\1\103", "\1\105\11\uffff\1\106\25\uffff\1\105\11\uffff\1\106", "\1\107\37\uffff\1\107", "\1\110\37\uffff\1\110", "\1\111\3\uffff\1\112\33\uffff\1\111\3\uffff\1\112", "\1\113\37\uffff\1\113", "\1\114\37\uffff\1\114", "\1\116\7\uffff\1\115\27\uffff\1\116\7\uffff\1\115", "\1\117\37\uffff\1\117", "", "", "\1\121\1\uffff\12\42", "", "", "", "", "", "",
			"\1\122\7\uffff\1\123\27\uffff\1\122\7\uffff\1\123", "\1\124\37\uffff\1\124", "\1\125\37\uffff\1\125", "\1\126\37\uffff\1\126", "\1\127\37\uffff\1\127", "\1\130\37\uffff\1\130", "\1\131\37\uffff\1\131", "\1\133\2\uffff\1\132\5\uffff\1\134\6\uffff\1\135\17\uffff\1\133\2\uffff" + "\1\132\5\uffff\1\134\6\uffff\1\135", "\1\136\37\uffff\1\136", "\1\137\37\uffff\1\137", "\1\140\37\uffff\1\140", "\1\141\37\uffff\1\141", "\12\40\7\uffff\3\40\1\143\16\40\1\145\1\144\6\40\4\uffff\1\40\1\uffff" + "\3\40\1\143\16\40\1\145\1\144\6\40", "\1\146\37\uffff\1\146", "\1\147\37\uffff\1\147", "\1\150\37\uffff\1\150", "\1\151\37\uffff\1\151", "\1\152\37\uffff\1\152", "\1\153\37\uffff\1\153", "\1\154\37\uffff\1\154", "\1\155\37\uffff\1\155",
			"\12\40\7\uffff\2\40\1\157\27\40\4\uffff\1\40\1\uffff\2\40\1\157\27\40", "\1\160\37\uffff\1\160", "\1\161\24\uffff\1\162\12\uffff\1\161\24\uffff\1\162", "\1\163\37\uffff\1\163", "\1\164\37\uffff\1\164", "\1\165\37\uffff\1\165", "\1\166\37\uffff\1\166", "\1\167\37\uffff\1\167", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\172\5\uffff\1\171\31\uffff\1\172\5\uffff\1\171", "\1\173\37\uffff\1\173", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\3\40\1\176\26\40\4\uffff\1\40\1\uffff\3\40\1\176\26\40", "\1\177\37\uffff\1\177", "\1\u0080\37\uffff\1\u0080", "\1\u0081\37\uffff\1\u0081", "\1\u0082\37\uffff\1\u0082", "\1\u0083\37\uffff\1\u0083", "", "", "\1\u0084\37\uffff\1\u0084", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
			"\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u0087\37\uffff\1\u0087", "\1\u0088\37\uffff\1\u0088", "\1\u0089\37\uffff\1\u0089", "\1\u008a\3\uffff\1\u008b\33\uffff\1\u008a\3\uffff\1\u008b", "\1\u008c\37\uffff\1\u008c", "\1\u008d\37\uffff\1\u008d", "\1\u008e\37\uffff\1\u008e", "\1\u008f\37\uffff\1\u008f", "\1\u0090\37\uffff\1\u0090", "\1\u0091\37\uffff\1\u0091", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u0093\37\uffff\1\u0093", "\1\u0094\37\uffff\1\u0094", "", "\1\u0095\37\uffff\1\u0095", "\12\40\7\uffff\16\40\1\u0097\13\40\4\uffff\1\40\1\uffff\16\40\1\u0097" + "\13\40", "\1\u0098\37\uffff\1\u0098", "\1\u0099\37\uffff\1\u0099", "\1\u009a\37\uffff\1\u009a", "\1\u009b\37\uffff\1\u009b", "\1\u009c\37\uffff\1\u009c",
			"\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u009f\37\uffff\1\u009f", "\1\u00a0\37\uffff\1\u00a0", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00a6\37\uffff\1\u00a6", "\1\u00a7\37\uffff\1\u00a7", "\1\u00a8\37\uffff\1\u00a8", "\1\u00a9\37\uffff\1\u00a9", "", "\1\u00aa\37\uffff\1\u00aa", "\1\u00ab\37\uffff\1\u00ab", "\1\u00ac\37\uffff\1\u00ac", "", "", "\1\u00ad\37\uffff\1\u00ad", "\1\u00ae\37\uffff\1\u00ae", "\1\u00af\37\uffff\1\u00af", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
			"\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00b2\37\uffff\1\u00b2", "\1\u00b3\37\uffff\1\u00b3", "", "", "\1\u00b4\37\uffff\1\u00b4", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00b6\37\uffff\1\u00b6", "\1\u00b7\37\uffff\1\u00b7", "\1\u00b8\37\uffff\1\u00b8", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00ba\37\uffff\1\u00ba", "\1\u00bb\37\uffff\1\u00bb", "\1\u00bc\37\uffff\1\u00bc", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00be\37\uffff\1\u00be", "", "\1\u00bf\37\uffff\1\u00bf", "\1\u00c0\37\uffff\1\u00c0", "\1\u00c1\37\uffff\1\u00c1", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00c3\37\uffff\1\u00c3", "\1\u00c4\37\uffff\1\u00c4", "\1\u00c5\37\uffff\1\u00c5", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
			"\1\u00c7\37\uffff\1\u00c7", "", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00c9", "", "", "", "", "", "\1\u00ca\37\uffff\1\u00ca", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00cc\37\uffff\1\u00cc", "\1\u00cd\37\uffff\1\u00cd", "\1\u00ce\37\uffff\1\u00ce", "\1\u00cf\37\uffff\1\u00cf", "\1\u00d0\37\uffff\1\u00d0", "\1\u00d1\37\uffff\1\u00d1", "\1\u00d2\37\uffff\1\u00d2", "\1\u00d3\37\uffff\1\u00d3", "", "", "\1\u00d4\37\uffff\1\u00d4", "\1\u00d5\37\uffff\1\u00d5", "\1\u00d6\37\uffff\1\u00d6", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00d8\37\uffff\1\u00d8", "\1\u00d9\37\uffff\1\u00d9", "", "\1\u00da\37\uffff\1\u00da", "\1\u00db\37\uffff\1\u00db", "\1\u00dc\37\uffff\1\u00dc", "", "\1\u00dd\37\uffff\1\u00dd",
			"\1\u00de\37\uffff\1\u00de", "\1\u00df\37\uffff\1\u00df", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "\1\u00e1\37\uffff\1\u00e1", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00e3\37\uffff\1\u00e3", "", "\1\u00e4\37\uffff\1\u00e4", "", "\1\u00e5\37\uffff\1\u00e5", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00e8\37\uffff\1\u00e8", "\1\u00e9\37\uffff\1\u00e9", "\1\u00ea\37\uffff\1\u00ea", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00ee\37\uffff\1\u00ee", "\1\u00ef\37\uffff\1\u00ef", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
			"\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "\1\u00f2\37\uffff\1\u00f2", "\1\u00f3\37\uffff\1\u00f3", "\1\u00f4\37\uffff\1\u00f4", "\1\u00f5\37\uffff\1\u00f5", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u00f7\37\uffff\1\u00f7", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "\1\u00fb\37\uffff\1\u00fb", "\1\u00fc\37\uffff\1\u00fc", "\1\u00fd\37\uffff\1\u00fd", "", "", "\1\u00fe\37\uffff\1\u00fe", "\1\u00ff\37\uffff\1\u00ff", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "", "\1\u0103\37\uffff\1\u0103",
			"\1\u0104\37\uffff\1\u0104", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "\1\u0107\37\uffff\1\u0107", "", "", "", "\1\u0108\37\uffff\1\u0108", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u010a\37\uffff\1\u010a", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u0110\37\uffff\1\u0110", "", "\1\u0111\37\uffff\1\u0111", "", "", "", "", "", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "\1\u0113\37\uffff\1\u0113", "", "\1\u0114\37\uffff\1\u0114", "\1\u0115\37\uffff\1\u0115",
			"\1\u0116\37\uffff\1\u0116", "\1\u0117\37\uffff\1\u0117", "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40", "" };

	static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);

	static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);

	static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);

	static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);

	static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);

	static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);

	static final short[][] DFA4_transition;
	static
	{
		int numStates = DFA4_transitionS.length;
		DFA4_transition = new short[numStates][];
		for (int i = 0; i < numStates; i++)
		{
			DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
		}
	}

	protected class DFA4 extends DFA
	{
		public DFA4(BaseRecognizer recognizer)
		{
			this.recognizer = recognizer;
			this.decisionNumber = 4;
			this.eot = DFA4_eot;
			this.eof = DFA4_eof;
			this.min = DFA4_min;
			this.max = DFA4_max;
			this.accept = DFA4_accept;
			this.special = DFA4_special;
			this.transition = DFA4_transition;
		}

		@Override
		public String getDescription()
		{
			return "1:1: Tokens : ( T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | SELECT | CREATE | DATABASE | DROP | USE | INDEX | TABLE | PRIMARY | KEY | NOT | NULL | DEFAULT | AUTO_INCREMENT | INT | FLOAT | CHAR | DATETIME | BOOLEAN | DECIMAL | TIMESTAMP | VARCHAR | INSERT | INTO | VALUES | DELETE | FROM | WHERE | UPDATE | SET | UNIQUE | ON | DISTINCT | GROUP | BY | HAVING | ORDER | ASC | DESC | AS | AVG | COUNT | MIN | MAX | SUM | AND | OR | EXISTS | ANY | IN | ALL | TRUE | FALSE | ID | STRING_LITERAL | INTEGER_LITERAL | FLOAT_LITERAL | WS );";
		}
	}
}
