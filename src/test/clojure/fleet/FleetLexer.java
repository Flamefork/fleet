// $ANTLR 3.2 Sep 23, 2009 14:05:07 ru/flamefork/fleet/Fleet.g 2010-01-10 23:41:19

	package fleet;


import org.antlr.runtime.*;

public class FleetLexer extends Lexer {
    public static final int SLIPWAY_CLOSE=5;
    public static final int SPACESHIP_CLOSE=7;
    public static final int SPACESHIP_OPEN=6;
    public static final int CHAR=8;
    public static final int SLIPWAY_OPEN=4;
    public static final int EOF=-1;

    	// Embed mode
    	boolean em = false;


    // delegates
    // delegators

    public FleetLexer() {;} 
    public FleetLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public FleetLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "ru/flamefork/fleet/Fleet.g"; }

    // $ANTLR start "SPACESHIP_OPEN"
    public final void mSPACESHIP_OPEN() throws RecognitionException {
        try {
            int _type = SPACESHIP_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ru/flamefork/fleet/Fleet.g:38:15: ({...}? => '<(' )
            // ru/flamefork/fleet/Fleet.g:39:2: {...}? => '<('
            {
            if ( !(( !em )) ) {
                throw new FailedPredicateException(input, "SPACESHIP_OPEN", " !em ");
            }
            match("<("); 

             em = true; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SPACESHIP_OPEN"

    // $ANTLR start "SPACESHIP_CLOSE"
    public final void mSPACESHIP_CLOSE() throws RecognitionException {
        try {
            int _type = SPACESHIP_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ru/flamefork/fleet/Fleet.g:43:16: ({...}? => ')>' )
            // ru/flamefork/fleet/Fleet.g:44:2: {...}? => ')>'
            {
            if ( !(( em )) ) {
                throw new FailedPredicateException(input, "SPACESHIP_CLOSE", " em ");
            }
            match(")>"); 

             em = false; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SPACESHIP_CLOSE"

    // $ANTLR start "SLIPWAY_OPEN"
    public final void mSLIPWAY_OPEN() throws RecognitionException {
        try {
            int _type = SLIPWAY_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ru/flamefork/fleet/Fleet.g:48:13: ({...}? => '\">' )
            // ru/flamefork/fleet/Fleet.g:49:2: {...}? => '\">'
            {
            if ( !(( em )) ) {
                throw new FailedPredicateException(input, "SLIPWAY_OPEN", " em ");
            }
            match("\">"); 

             em = false; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLIPWAY_OPEN"

    // $ANTLR start "SLIPWAY_CLOSE"
    public final void mSLIPWAY_CLOSE() throws RecognitionException {
        try {
            int _type = SLIPWAY_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ru/flamefork/fleet/Fleet.g:53:14: ({...}? => '<\"' )
            // ru/flamefork/fleet/Fleet.g:54:2: {...}? => '<\"'
            {
            if ( !(( !em )) ) {
                throw new FailedPredicateException(input, "SLIPWAY_CLOSE", " !em ");
            }
            match("<\""); 

             em = true; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLIPWAY_CLOSE"

    // $ANTLR start "CHAR"
    public final void mCHAR() throws RecognitionException {
        try {
            int _type = CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ru/flamefork/fleet/Fleet.g:58:5: ( . )
            // ru/flamefork/fleet/Fleet.g:58:7: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CHAR"

    public void mTokens() throws RecognitionException {
        // ru/flamefork/fleet/Fleet.g:1:8: ( SPACESHIP_OPEN | SPACESHIP_CLOSE | SLIPWAY_OPEN | SLIPWAY_CLOSE | CHAR )
        int alt1=5;
        int LA1_0 = input.LA(1);

        if ( (LA1_0=='<') ) {
            int LA1_1 = input.LA(2);

            if ( (LA1_1=='(') && (( !em ))) {
                alt1=1;
            }
            else if ( (LA1_1=='\"') && (( !em ))) {
                alt1=4;
            }
            else {
                alt1=5;}
        }
        else if ( (LA1_0==')') ) {
            int LA1_2 = input.LA(2);

            if ( (LA1_2=='>') && (( em ))) {
                alt1=2;
            }
            else {
                alt1=5;}
        }
        else if ( (LA1_0=='\"') ) {
            int LA1_3 = input.LA(2);

            if ( (LA1_3=='>') && (( em ))) {
                alt1=3;
            }
            else {
                alt1=5;}
        }
        else if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='(')||(LA1_0>='*' && LA1_0<=';')||(LA1_0>='=' && LA1_0<='\uFFFF')) ) {
            alt1=5;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("", 1, 0, input);

            throw nvae;
        }
        switch (alt1) {
            case 1 :
                // ru/flamefork/fleet/Fleet.g:1:10: SPACESHIP_OPEN
                {
                mSPACESHIP_OPEN(); 

                }
                break;
            case 2 :
                // ru/flamefork/fleet/Fleet.g:1:25: SPACESHIP_CLOSE
                {
                mSPACESHIP_CLOSE(); 

                }
                break;
            case 3 :
                // ru/flamefork/fleet/Fleet.g:1:41: SLIPWAY_OPEN
                {
                mSLIPWAY_OPEN(); 

                }
                break;
            case 4 :
                // ru/flamefork/fleet/Fleet.g:1:54: SLIPWAY_CLOSE
                {
                mSLIPWAY_CLOSE(); 

                }
                break;
            case 5 :
                // ru/flamefork/fleet/Fleet.g:1:68: CHAR
                {
                mCHAR(); 

                }
                break;

        }

    }


 

}