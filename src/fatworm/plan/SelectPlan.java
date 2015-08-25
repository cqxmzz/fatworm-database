package fatworm.plan;

import java.util.LinkedList;

import fatworm.database.Column;
import fatworm.database.Schema;
import fatworm.pred.CopPred;
import fatworm.pred.OrPred;
import fatworm.pred.Predicate;
import fatworm.scan.Scan;
import fatworm.scan.SelectScan;
import fatworm.scan.UpdateScan;

public class SelectPlan implements Plan
{
	private Plan plan;

	Plan fatherPlan;

	Predicate predicate;

	public SelectPlan(Plan p, Predicate pred)
	{
		plan = p;
		plan.setFather(this);
		predicate = pred;
	}

	@Override
	public UpdateScan open()
	{
		Scan scan = plan.open();
		return new SelectScan(scan, predicate);
	}

	@Override
	public Schema schema()
	{
		return plan.schema();
	}

	@Override
	public LinkedList<Plan> son()
	{
		LinkedList<Plan> ret = new LinkedList<Plan>();
		ret.add(plan);
		return ret;
	}

	@Override
	public void setSon(LinkedList<Plan> plans)
	{
		plan = plans.get(0);
	}

	@Override
	public Plan father()
	{
		return fatherPlan;
	}

	@Override
	public void setFather(Plan plan)
	{
		fatherPlan = plan;
	}

	public void pushDown()
	{
		Plan sonPlan = plan;
		Plan fatherPlan = father();
		if (sonPlan instanceof ProjectPlan || sonPlan instanceof SortPlan || sonPlan instanceof SelectPlan)
		{
			Plan sonsonPlan = sonPlan.son().get(0);
			LinkedList<Plan> tmp = fatherPlan.son();
			tmp.set(0, sonPlan);
			if (fatherPlan != null)
			{
				fatherPlan.setSon(tmp);
			}
			sonPlan.setFather(fatherPlan);
			tmp = new LinkedList<Plan>();
			tmp.add(this);
			sonPlan.setSon(tmp);
			this.setFather(sonPlan);
			tmp = new LinkedList<Plan>();
			tmp.add(sonsonPlan);
			this.setSon(tmp);
			sonsonPlan.setFather(this);
			this.pushDown();
		}
		else if (sonPlan instanceof RenamePlan)
		{
			RenamePlan renamePlan = (RenamePlan) sonPlan;
			LinkedList<String> t1 = new LinkedList<String>();
			LinkedList<String> t2 = new LinkedList<String>();
			LinkedList<Column> c1 = new LinkedList<Column>();
			t1.add(renamePlan.name);
			t2.add("");
			c1.add(new Column(null, false, false, false, null));
			if (predicate.dontNeedSchema(new Schema(c1, t1, t2)))
			{
				Plan sonsonPlan = sonPlan.son().get(0);
				LinkedList<Plan> tmp = fatherPlan.son();
				tmp.set(0, sonPlan);
				if (fatherPlan != null)
				{
					fatherPlan.setSon(tmp);
				}
				sonPlan.setFather(fatherPlan);
				tmp = new LinkedList<Plan>();
				tmp.add(this);
				sonPlan.setSon(tmp);
				this.setFather(sonPlan);
				tmp = new LinkedList<Plan>();
				tmp.add(sonsonPlan);
				this.setSon(tmp);
				sonsonPlan.setFather(this);
				this.pushDown();
			}
		}
		else if (sonPlan instanceof ProductPlan)
		{
			ProductPlan productPlan = (ProductPlan) sonPlan;
			if (predicate instanceof CopPred || predicate instanceof OrPred)
			{
				Plan leftPlan = productPlan.son().get(0);
				Plan rightPlan = productPlan.son().get(1);
				if (rightPlan instanceof RenamePlan || rightPlan instanceof TablePlan)
				{
					if (predicate.dontNeedSchema(rightPlan.schema()))
					{
						productPlan.setFather(father());
						LinkedList<Plan> tmp = father().son();
						tmp.set(0, productPlan);
						father().setSon(tmp);
						plan = leftPlan;
						leftPlan.setFather(this);
						productPlan.plan1 = this;
						setFather(productPlan);
						this.pushDown();
					}
					else if (!predicate.dontNeedSchema(rightPlan.schema()) && leftPlan instanceof ProductPlan)
					{
						Plan leftleftPlan = leftPlan.son().get(0);
						Plan leftrightPlan = leftPlan.son().get(1);
						// if (predicate.notNeed(leftleftPlan.schema()) &&
						// (leftleftPlan instanceof TablePlan))
						// {
						// LinkedList<Plan> tmp = new LinkedList<Plan>();
						// tmp.add(new ProductPlan(new
						// ProductPlan(leftrightPlan, rightPlan),
						// leftleftPlan));
						// this.setSon(tmp);
						// this.son().get(0).setFather(this);
						// this.pushDown();
						// }
						// else
						if (predicate.dontNeedSchema(leftrightPlan.schema()))
						{
							LinkedList<Plan> tmp = new LinkedList<Plan>();
							tmp.add(new ProductPlan(new ProductPlan(leftleftPlan, rightPlan), leftrightPlan));
							this.setSon(tmp);
							this.son().get(0).setFather(this);
							this.pushDown();
						}
					}
				}
			}
		}
	}

	@Override
	public String tostring()
	{
		return "Select from " + plan.tostring() + " where " + predicate.toString();
	}
}
