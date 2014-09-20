author: tangbo, jiangxiao, chengyu

C 组在写 fatworm 过程当中积累的测试数据 



fatworm-create:
	acm, fatworm 为两张比较小的表格，主要为了正确性测试
	A, B, C, D, E 的 tuple 为 (int, varchar(5))，大小分别为 50, 6, 200, 20, 80，用于测试 JoinOrder 等
	
fatworm-select:
	比较多，一一列举
	# testNaiveSelect, testProduct, testOrderBy, testUnique: 简单的正确性测试
	# testCalcSelect：select 1+2 系列的 query，有带和不带 from 的
	# testSubquery: from 后的 subquery，where 里的 subquery(in, exist, all...)
					加大数据量可以测试 uncorrelated subquery elimination
					select id, (select name) from student
	# testGroupBy: 对空表的  count，select 后出现 aggFunc，having 里出现 aggFunc
	# testJoinOrder: 将 A,B,C,D 四个表 product 起来，要先乘小的表，并且要 selection pushdown
					 对10+个表的 product，因为我们超过10个表就贪心不DP了
	# testMergeJoin: 一个也算 testAlias 的 Query，MergeJoin 要比不 materialize 快很多
;

fatworm-index:
	简单的 index 测试

fatworm-auto:
	auto 正确性测试，以 mysql 为标准
		
fatworm-concurrency:
	共有 18 个线程同时操作这个 database，其中 4 个线程 write，其它的 read
	Query 由之前的所有 Query 重复加打乱顺序组成，包括同时排序
	没有任何读写冲突，即任何顺序的执行结果都一样

fatworm-update:
	有这样的 update：set id=id+1000, id=id-1000
	也有 where 里带 subquery 的 update
