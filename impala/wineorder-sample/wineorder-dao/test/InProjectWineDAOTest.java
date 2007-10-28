

import interfaces.WineDAO;

import java.util.Collection;

import net.java.impala.spring.plugin.ParentSpec;
import net.java.impala.spring.plugin.SimplePluginSpecBuilder;
import net.java.impala.testrun.DynamicContextHolder;
import net.java.impala.testrun.PluginTestRunner;
import test.BaseDataTest;
import classes.Wine;

public class InProjectWineDAOTest extends BaseDataTest {

	public static void main(String[] args) {
		System.setProperty("impala.parent.project", "wineorder");
		PluginTestRunner.run(InProjectWineDAOTest.class);
	}

	public void testDAO() {

		WineDAO dao = DynamicContextHolder.getBean(this, "wineDAO", WineDAO.class);

		Wine wine = new Wine();
		wine.setColor("red");
		wine.setVineyard("Chateau X");
		wine.setTitle("Cabernet");
		wine.setVintage(1996);
		dao.save(wine);

		Collection<Wine> winesOfVintage = dao.getWinesOfVintage(1996);
		System.out.println("Wines of vintage 1996: " + winesOfVintage.size());
		assertEquals(1, winesOfVintage.size());

		wine.setVintage(2000);
		wine.setColor("rose");
		dao.update(wine);

		Wine updated = dao.findById(wine.getId());
		assertEquals(2000, updated.getVintage());

	}

	public ParentSpec getPluginSpec() {
		return new SimplePluginSpecBuilder("parent-context.xml", new String[] { "wineorder-dao", "wineorder-hibernate" }).getParentSpec();
	}

}