// $ANTLR 3.2 Sep 23, 2009 12:02:23 Fleet.g 2009-12-30 02:50:23

	package ru.flamefork.fleet.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class FleetParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SLIPWAY_OPEN", "SLIPWAY_CLOSE", "SPACESHIP_OPEN", "SPACESHIP_CLOSE", "CHAR"
    };
    public static final int SLIPWAY_CLOSE=5;
    public static final int SPACESHIP_CLOSE=7;
    public static final int SPACESHIP_OPEN=6;
    public static final int CHAR=8;
    public static final int SLIPWAY_OPEN=4;
    public static final int EOF=-1;

    // delegates
    // delegators


        public FleetParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public FleetParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return FleetParser.tokenNames; }
    public String getGrammarFileName() { return "Fleet.g"; }


    public static class input_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "input"
    // Fleet.g:20:1: input : template ;
    public final FleetParser.input_return input() throws RecognitionException {
        FleetParser.input_return retval = new FleetParser.input_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        FleetParser.template_return template1 = null;



        try {
            // Fleet.g:20:6: ( template )
            // Fleet.g:21:2: template
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_template_in_input47);
            template1=template();

            state._fsp--;

            adaptor.addChild(root_0, template1.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "input"

    public static class template_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "template"
    // Fleet.g:23:1: template : ( text )? ( spaceship ( text )? )* ;
    public final FleetParser.template_return template() throws RecognitionException {
        FleetParser.template_return retval = new FleetParser.template_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        FleetParser.text_return text2 = null;

        FleetParser.spaceship_return spaceship3 = null;

        FleetParser.text_return text4 = null;



        try {
            // Fleet.g:23:9: ( ( text )? ( spaceship ( text )? )* )
            // Fleet.g:24:2: ( text )? ( spaceship ( text )? )*
            {
            root_0 = (Object)adaptor.nil();

            // Fleet.g:24:2: ( text )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==CHAR) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // Fleet.g:24:2: text
                    {
                    pushFollow(FOLLOW_text_in_template55);
                    text2=text();

                    state._fsp--;

                    adaptor.addChild(root_0, text2.getTree());

                    }
                    break;

            }

            // Fleet.g:24:8: ( spaceship ( text )? )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==SPACESHIP_OPEN) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // Fleet.g:24:9: spaceship ( text )?
            	    {
            	    pushFollow(FOLLOW_spaceship_in_template59);
            	    spaceship3=spaceship();

            	    state._fsp--;

            	    adaptor.addChild(root_0, spaceship3.getTree());
            	    // Fleet.g:24:19: ( text )?
            	    int alt2=2;
            	    int LA2_0 = input.LA(1);

            	    if ( (LA2_0==CHAR) ) {
            	        alt2=1;
            	    }
            	    switch (alt2) {
            	        case 1 :
            	            // Fleet.g:24:19: text
            	            {
            	            pushFollow(FOLLOW_text_in_template61);
            	            text4=text();

            	            state._fsp--;

            	            adaptor.addChild(root_0, text4.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "template"

    public static class embed_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "embed"
    // Fleet.g:26:1: embed : ( text )? ( slipway ( text )? )* ;
    public final FleetParser.embed_return embed() throws RecognitionException {
        FleetParser.embed_return retval = new FleetParser.embed_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        FleetParser.text_return text5 = null;

        FleetParser.slipway_return slipway6 = null;

        FleetParser.text_return text7 = null;



        try {
            // Fleet.g:26:6: ( ( text )? ( slipway ( text )? )* )
            // Fleet.g:27:2: ( text )? ( slipway ( text )? )*
            {
            root_0 = (Object)adaptor.nil();

            // Fleet.g:27:2: ( text )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==CHAR) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Fleet.g:27:2: text
                    {
                    pushFollow(FOLLOW_text_in_embed72);
                    text5=text();

                    state._fsp--;

                    adaptor.addChild(root_0, text5.getTree());

                    }
                    break;

            }

            // Fleet.g:27:8: ( slipway ( text )? )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==SLIPWAY_OPEN) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Fleet.g:27:9: slipway ( text )?
            	    {
            	    pushFollow(FOLLOW_slipway_in_embed76);
            	    slipway6=slipway();

            	    state._fsp--;

            	    adaptor.addChild(root_0, slipway6.getTree());
            	    // Fleet.g:27:17: ( text )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0==CHAR) ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // Fleet.g:27:17: text
            	            {
            	            pushFollow(FOLLOW_text_in_embed78);
            	            text7=text();

            	            state._fsp--;

            	            adaptor.addChild(root_0, text7.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "embed"

    public static class slipway_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "slipway"
    // Fleet.g:29:1: slipway : SLIPWAY_OPEN template SLIPWAY_CLOSE ;
    public final FleetParser.slipway_return slipway() throws RecognitionException {
        FleetParser.slipway_return retval = new FleetParser.slipway_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SLIPWAY_OPEN8=null;
        Token SLIPWAY_CLOSE10=null;
        FleetParser.template_return template9 = null;


        Object SLIPWAY_OPEN8_tree=null;
        Object SLIPWAY_CLOSE10_tree=null;

        try {
            // Fleet.g:29:8: ( SLIPWAY_OPEN template SLIPWAY_CLOSE )
            // Fleet.g:30:2: SLIPWAY_OPEN template SLIPWAY_CLOSE
            {
            root_0 = (Object)adaptor.nil();

            SLIPWAY_OPEN8=(Token)match(input,SLIPWAY_OPEN,FOLLOW_SLIPWAY_OPEN_in_slipway89); 
            SLIPWAY_OPEN8_tree = (Object)adaptor.create(SLIPWAY_OPEN8);
            root_0 = (Object)adaptor.becomeRoot(SLIPWAY_OPEN8_tree, root_0);

            pushFollow(FOLLOW_template_in_slipway92);
            template9=template();

            state._fsp--;

            adaptor.addChild(root_0, template9.getTree());
            SLIPWAY_CLOSE10=(Token)match(input,SLIPWAY_CLOSE,FOLLOW_SLIPWAY_CLOSE_in_slipway94); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "slipway"

    public static class spaceship_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "spaceship"
    // Fleet.g:32:1: spaceship : SPACESHIP_OPEN embed SPACESHIP_CLOSE ;
    public final FleetParser.spaceship_return spaceship() throws RecognitionException {
        FleetParser.spaceship_return retval = new FleetParser.spaceship_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SPACESHIP_OPEN11=null;
        Token SPACESHIP_CLOSE13=null;
        FleetParser.embed_return embed12 = null;


        Object SPACESHIP_OPEN11_tree=null;
        Object SPACESHIP_CLOSE13_tree=null;

        try {
            // Fleet.g:32:10: ( SPACESHIP_OPEN embed SPACESHIP_CLOSE )
            // Fleet.g:33:2: SPACESHIP_OPEN embed SPACESHIP_CLOSE
            {
            root_0 = (Object)adaptor.nil();

            SPACESHIP_OPEN11=(Token)match(input,SPACESHIP_OPEN,FOLLOW_SPACESHIP_OPEN_in_spaceship103); 
            SPACESHIP_OPEN11_tree = (Object)adaptor.create(SPACESHIP_OPEN11);
            root_0 = (Object)adaptor.becomeRoot(SPACESHIP_OPEN11_tree, root_0);

            pushFollow(FOLLOW_embed_in_spaceship106);
            embed12=embed();

            state._fsp--;

            adaptor.addChild(root_0, embed12.getTree());
            SPACESHIP_CLOSE13=(Token)match(input,SPACESHIP_CLOSE,FOLLOW_SPACESHIP_CLOSE_in_spaceship108); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "spaceship"

    public static class text_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "text"
    // Fleet.g:35:1: text : ( options {greedy=false; } : CHAR )+ ;
    public final FleetParser.text_return text() throws RecognitionException {
        FleetParser.text_return retval = new FleetParser.text_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CHAR14=null;

        Object CHAR14_tree=null;

        try {
            // Fleet.g:35:5: ( ( options {greedy=false; } : CHAR )+ )
            // Fleet.g:36:2: ( options {greedy=false; } : CHAR )+
            {
            root_0 = (Object)adaptor.nil();

            // Fleet.g:36:2: ( options {greedy=false; } : CHAR )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==CHAR) ) {
                    alt7=1;
                }
                else if ( (LA7_0==EOF||(LA7_0>=SLIPWAY_OPEN && LA7_0<=SPACESHIP_CLOSE)) ) {
                    alt7=2;
                }


                switch (alt7) {
            	case 1 :
            	    // Fleet.g:36:27: CHAR
            	    {
            	    CHAR14=(Token)match(input,CHAR,FOLLOW_CHAR_in_text126); 
            	    CHAR14_tree = (Object)adaptor.create(CHAR14);
            	    adaptor.addChild(root_0, CHAR14_tree);


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "text"

    // Delegated rules


 

    public static final BitSet FOLLOW_template_in_input47 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_in_template55 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_spaceship_in_template59 = new BitSet(new long[]{0x0000000000000142L});
    public static final BitSet FOLLOW_text_in_template61 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_text_in_embed72 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_slipway_in_embed76 = new BitSet(new long[]{0x0000000000000112L});
    public static final BitSet FOLLOW_text_in_embed78 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SLIPWAY_OPEN_in_slipway89 = new BitSet(new long[]{0x0000000000000160L});
    public static final BitSet FOLLOW_template_in_slipway92 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_SLIPWAY_CLOSE_in_slipway94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SPACESHIP_OPEN_in_spaceship103 = new BitSet(new long[]{0x0000000000000190L});
    public static final BitSet FOLLOW_embed_in_spaceship106 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SPACESHIP_CLOSE_in_spaceship108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CHAR_in_text126 = new BitSet(new long[]{0x0000000000000102L});

}