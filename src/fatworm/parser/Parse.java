package fatworm.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;

import fatworm.parser.FatwormParser.statement_return;

public class Parse
{
	public Parse()
	{
	}

	synchronized public boolean parse(String str)
	{
		try
		{
			ANTLRStringStream antlrStringStream = new ANTLRStringStream(str);
			FatwormLexer eLexer = new FatwormLexer(antlrStringStream);
			TokenRewriteStream tokens = new TokenRewriteStream(eLexer);
			FatwormParser grammar = new FatwormParser(tokens);
			grammar.setTreeAdaptor(new CommonTreeAdaptor());
			statement_return ret = grammar.statement();
			CommonTree tree = (CommonTree) ret.getTree();
			TreeTraverse treeTraverse = new TreeTraverse();
			treeTraverse.traverse(tree);
			// treeTraverse.printTree(tree, 0);
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
