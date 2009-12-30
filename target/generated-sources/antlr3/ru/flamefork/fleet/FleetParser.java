// $ANTLR 3.2 Sep 23, 2009 14:05:07 ru/flamefork/fleet/Fleet.g 2009-12-31 02:14:54

	package ru.flamefork.fleet;


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
    public String getGrammarFileName() { return "ru/flamefork/fleet/Fleet.g"; }


    public static class input_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "input"
    // ru/flamefork/fleet/Fleet.g:20:1: input : template ;
    public final FleetParser.input_return input() throws RecognitionException {
        FleetParser.input_return retval = new FleetParser.input_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        FleetParser.template_return template1 = null;



        try {
            // ru/flamefork/fleet/Fleet.g:20:6: ( template )
            // ru/flamefork/fleet/Fleet.g:21:2: template
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
    // ru/flamefork/fleet/Fleet.g:23:1: template : ( text )? ( spaceship ( text )? )* ;
    public final FleetParser.template_return template() throws RecognitionException {
        FleetParser.template_return retval = new FleetParser.template_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        FleetParser.text_return text2 = null;

        FleetParser.spaceship_return spaceship3 = null;

        FleetParser.text_return text4 = null;



        try {
            // ru/flamefork/fleet/Fleet.g:23:9: ( ( text )? ( spaceship ( text )? )* )
            // ru/flamefork/fleet/Fleet.g:24:2: ( text )? ( spaceship ( text )? )*
            {
            root_0 = (Object)adaptor.nil();

            // ru/flamefork/fleet/Fleet.g:24:2: ( text )?
            int alt1=2;
            switch ( input.LA(1) ) {
                case CHAR:
                    {
                    alt1=1;
                    }
                    break;
            }

            switch (alt1) {
                case 1 :
                    // ru/flamefork/fleet/Fleet.g:24:2: text
                    {
                    pushFollow(FOLLOW_text_in_template55);
                    text2=text();

                    state._fsp--;

                    adaptor.addChild(root_0, text2.getTree());

                    }
                    break;

            }

            // ru/flamefork/fleet/Fleet.g:24:8: ( spaceship ( text )? )*
            loop3:
            do {
                int alt3=2;
                switch ( input.LA(1) ) {
                case SPACESHIP_OPEN:
                    {
                    alt3=1;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // ru/flamefork/fleet/Fleet.g:24:9: spaceship ( text )?
            	    {
            	    pushFollow(FOLLOW_spaceship_in_template59);
            	    spaceship3=spaceship();

            	    state._fsp--;

            	    adaptor.addChild(root_0, spaceship3.getTree());
            	    // ru/flamefork/fleet/Fleet.g:24:19: ( text )?
            	    int alt2=2;
            	    switch ( input.LA(1) ) {
            	        case CHAR:
            	            {
            	            alt2=1;
            	            }
            	            break;
            	    }

            	    switch (alt2) {
            	        case 1 :
            	            // ru/flamefork/fleet/Fleet.g:24:19: text
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
    // ru/flamefork/fleet/Fleet.g:26:1: embed : ( text )? ( slipway ( text )? )* ;
    public final FleetParser.embed_return embed() throws RecognitionException {
        FleetParser.embed_return retval = new FleetParser.embed_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        FleetParser.text_return text5 = null;

        FleetParser.slipway_return slipway6 = null;

        FleetParser.text_return text7 = null;



        try {
            // ru/flamefork/fleet/Fleet.g:26:6: ( ( text )? ( slipway ( text )? )* )
            // ru/flamefork/fleet/Fleet.g:27:2: ( text )? ( slipway ( text )? )*
            {
            root_0 = (Object)adaptor.nil();

            // ru/flamefork/fleet/Fleet.g:27:2: ( text )?
            int alt4=2;
            switch ( input.LA(1) ) {
                case CHAR:
                    {
                    alt4=1;
                    }
                    break;
            }

            switch (alt4) {
                case 1 :
                    // ru/flamefork/fleet/Fleet.g:27:2: text
                    {
                    pushFollow(FOLLOW_text_in_embed72);
                    text5=text();

                    state._fsp--;

                    adaptor.addChild(root_0, text5.getTree());

                    }
                    break;

            }

            // ru/flamefork/fleet/Fleet.g:27:8: ( slipway ( text )? )*
            loop6:
            do {
                int alt6=2;
                switch ( input.LA(1) ) {
                case SLIPWAY_OPEN:
                    {
                    alt6=1;
                    }
                    break;

                }

                switch (alt6) {
            	case 1 :
            	    // ru/flamefork/fleet/Fleet.g:27:9: slipway ( text )?
            	    {
            	    pushFollow(FOLLOW_slipway_in_embed76);
            	    slipway6=slipway();

            	    state._fsp--;

            	    adaptor.addChild(root_0, slipway6.getTree());
            	    // ru/flamefork/fleet/Fleet.g:27:17: ( text )?
            	    int alt5=2;
            	    switch ( input.LA(1) ) {
            	        case CHAR:
            	            {
            	            alt5=1;
            	            }
            	            break;
            	    }

            	    switch (alt5) {
            	        case 1 :
            	            // ru/flamefork/fleet/Fleet.g:27:17: text
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
    // ru/flamefork/fleet/Fleet.g:29:1: slipway : SLIPWAY_OPEN template SLIPWAY_CLOSE ;
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
            // ru/flamefork/fleet/Fleet.g:29:8: ( SLIPWAY_OPEN template SLIPWAY_CLOSE )
            // ru/flamefork/fleet/Fleet.g:30:2: SLIPWAY_OPEN template SLIPWAY_CLOSE
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
    // ru/flamefork/fleet/Fleet.g:32:1: spaceship : SPACESHIP_OPEN embed SPACESHIP_CLOSE ;
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
            // ru/flamefork/fleet/Fleet.g:32:10: ( SPACESHIP_OPEN embed SPACESHIP_CLOSE )
            // ru/flamefork/fleet/Fleet.g:33:2: SPACESHIP_OPEN embed SPACESHIP_CLOSE
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
    // ru/flamefork/fleet/Fleet.g:35:1: text : CHAR ( options {greedy=false; } : CHAR )* ;
    public final FleetParser.text_return text() throws RecognitionException {
        FleetParser.text_return retval = new FleetParser.text_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CHAR14=null;
        Token CHAR15=null;

        Object CHAR14_tree=null;
        Object CHAR15_tree=null;

        try {
            // ru/flamefork/fleet/Fleet.g:35:5: ( CHAR ( options {greedy=false; } : CHAR )* )
            // ru/flamefork/fleet/Fleet.g:36:2: CHAR ( options {greedy=false; } : CHAR )*
            {
            root_0 = (Object)adaptor.nil();

            CHAR14=(Token)match(input,CHAR,FOLLOW_CHAR_in_text117); 
            CHAR14_tree = (Object)adaptor.create(CHAR14);
            root_0 = (Object)adaptor.becomeRoot(CHAR14_tree, root_0);

            // ru/flamefork/fleet/Fleet.g:36:8: ( options {greedy=false; } : CHAR )*
            loop7:
            do {
                int alt7=2;
                switch ( input.LA(1) ) {
                case CHAR:
                    {
                    alt7=1;
                    }
                    break;
                case EOF:
                case SLIPWAY_OPEN:
                case SLIPWAY_CLOSE:
                case SPACESHIP_OPEN:
                case SPACESHIP_CLOSE:
                    {
                    alt7=2;
                    }
                    break;

                }

                switch (alt7) {
            	case 1 :
            	    // ru/flamefork/fleet/Fleet.g:36:33: CHAR
            	    {
            	    CHAR15=(Token)match(input,CHAR,FOLLOW_CHAR_in_text129); 
            	    CHAR15_tree = (Object)adaptor.create(CHAR15);
            	    adaptor.addChild(root_0, CHAR15_tree);


            	    }
            	    break;

            	default :
            	    break loop7;
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
    public static final BitSet FOLLOW_CHAR_in_text117 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_CHAR_in_text129 = new BitSet(new long[]{0x0000000000000102L});

}