package generator;

import org.junit.Test;

import com.jointt.generator.core.PoushengGenerator;
import com.jointt.generator.core.model.SubTableVO;
import com.jointt.generator.core.model.TableVO;

public class GeneratorClient {

	/**
	 * 单表(内嵌页)
	 */
	@Test
	public void generatorOneTableInner() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("Energy"); //设置类名....
			tableVO.setPackageName("basic");//包名.....
			tableVO.setTableName("j_energy"); //数据库表名..
			PoushengGenerator.generatorOneTableInner(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单表(弹窗)
	 */
	@Test
	public void generatorOneTableModel() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("GroupRole");//设置类名
			tableVO.setPackageName("sys");//包名
			tableVO.setTableName("sys_group_role");//数据库表名
			PoushengGenerator.generatorOneTableModel(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单表(树结构)
	 */
	@Test
	public void generatorOneTableTree() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("MenuTree");//设置类名
			tableVO.setPackageName("menutree");//包名
			tableVO.setTableName("com_menu");//数据库表名
			tableVO.setTreeSetting("MENU_ID", "PARENT_ID", "NAME"); //树形结构的参数设置，(id,pid,查询名称)
			PoushengGenerator.generatorOneTableTree(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 一主多从
	 */
	@Test
	public void generatorOneToMany() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("Team");
			tableVO.setPackageName("team");
			tableVO.setTableName("t_team");

			SubTableVO sub = new SubTableVO();
			sub.setClassName("TeamPlayer");
			sub.setPackageName("team");
			sub.setTableName("t_team_player");

			sub.setRelationKeys("TEAM_ID", "TEAM_ID"); //设置与主表的关联key

			tableVO.addSubTable(sub);

			SubTableVO sub1 = new SubTableVO();
			sub1.setClassName("TeamCoach");
			sub1.setPackageName("team");
			sub1.setTableName("t_team_coach");

			sub1.setRelationKeys("TEAM_ID", "TEAM_ID"); //设置与主表的关联key

			tableVO.addSubTable(sub1);

			SubTableVO sub2 = new SubTableVO();
			sub2.setClassName("TeamManager");
			sub2.setPackageName("team");
			sub2.setTableName("t_team_manager");

			sub2.setRelationKeys("TEAM_ID", "TEAM_ID");//设置与主表的关联key

			tableVO.addSubTable(sub2);

			PoushengGenerator.generatorOneToMany(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 一主多从(树结构)
	 */
	@Test
	public void generatorOneToManyTree() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("TeamTree");
			tableVO.setPackageName("teamtree");
			tableVO.setTableName("t_team");
			tableVO.setTreeSetting("TEAM_ID", "TEAM_ID", "TEAM_NAME"); //树形结构的设置

			SubTableVO sub = new SubTableVO();
			sub.setClassName("TeamPlayerTree");
			sub.setPackageName("teamtree");
			sub.setTableName("t_team_player");

			sub.setRelationKeys("TEAM_ID", "TEAM_ID");

			tableVO.addSubTable(sub);

			SubTableVO sub1 = new SubTableVO();
			sub1.setClassName("TeamCoachTree");
			sub1.setPackageName("teamtree");
			sub1.setTableName("t_team_coach");

			sub1.setRelationKeys("TEAM_ID", "TEAM_ID");

			tableVO.addSubTable(sub1);

			SubTableVO sub2 = new SubTableVO();
			sub2.setClassName("TeamManagerTree");
			sub2.setPackageName("teamtree");
			sub2.setTableName("t_team_manager");

			sub2.setRelationKeys("TEAM_ID", "TEAM_ID");
			tableVO.addSubTable(sub2);
			PoushengGenerator.generatorOneToManyTree(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 一主多从2
	 */
	@Test
	public void generatorOneToMany2() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("Master");
			tableVO.setPackageName("master");
			tableVO.setTableName("t_master");

			SubTableVO sub = new SubTableVO();
			sub.setClassName("Detail");
			sub.setPackageName("master");
			sub.setTableName("t_detail");

			sub.setRelationKeys("BILL_NO", "BILL_NO");

			tableVO.addSubTable(sub);

			PoushengGenerator.generatorOneToMany(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主从（弹窗）
	 */
	@Test
	public void generatorMasterDetailModel() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("MemberModel");
			tableVO.setPackageName("membermodel");
			tableVO.setTableName("t_member");

			SubTableVO sub = new SubTableVO();
			sub.setClassName("MemberInfoModel");
			sub.setPackageName("membermodel");
			sub.setTableName("t_member_info");

			sub.setRelationKeys("MEMBER_ID", "MEMBER_ID"); //设置主表和从表的关联key

			tableVO.addSubTable(sub);

			PoushengGenerator.generatorMasterDetailModel(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主从（内嵌页）
	 */
	@Test
	public void generatorMasterDetailInner() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("Member");
			tableVO.setPackageName("member");
			tableVO.setTableName("t_member");

			SubTableVO sub = new SubTableVO();
			sub.setClassName("MemberInfo");
			sub.setPackageName("member");
			sub.setTableName("t_member_info");

			sub.setRelationKeys("MEMBER_ID", "MEMBER_ID");

			tableVO.addSubTable(sub);

			PoushengGenerator.generatorMasterDetailInner(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 主多从（内嵌页）测试
	 */
	@Test
	public void generatorMasterDetailInner2() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("TeamMaster");
			tableVO.setPackageName("teammaster");
			tableVO.setTableName("t_team");
			tableVO.setTreeSetting("TEAM_ID", "TEAM_ID", "TEAM_NAME");

			SubTableVO sub = new SubTableVO();
			sub.setClassName("TeamPlayerDetail");
			sub.setPackageName("teammaster");
			sub.setTableName("t_team_player");

			sub.setRelationKeys("TEAM_ID", "TEAM_ID");

			tableVO.addSubTable(sub);

			SubTableVO sub1 = new SubTableVO();
			sub1.setClassName("TeamCoachDetail");
			sub1.setPackageName("teammaster");
			sub1.setTableName("t_team_coach");

			sub1.setRelationKeys("TEAM_ID", "TEAM_ID");

			tableVO.addSubTable(sub1);

			PoushengGenerator.generatorMasterDetailInner(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 多对多-中间表
	 */
	@Test
	public void generatorManyToMany() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("Student");
			tableVO.setPackageName("student");
			tableVO.setTableName("t_student");
			tableVO.setRelationTableName("t_course");
			// 关联的另一边表设置
			tableVO.setRelationTableDataUrl("course/dataGrid");
			// 中间表的设置
			tableVO.setMiddleTableUrl("course/dataGrid", "course/insert", "course/deleteAll");

			SubTableVO sub = new SubTableVO();
			sub.setClassName("StudentCourse");
			sub.setPackageName("student");
			sub.setTableName("t_student_course");

			sub.setRelationKeys("STUDENT_ID", "STUDENT_ID");
			tableVO.addSubTable(sub);

			PoushengGenerator.generatorManyToMany(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new com.jointt.generator.core.window.GeneratorWindow().pack();
	}
	
	/**
	 * 多对多-中间表
	 */
	@Test
	public void testGeneratorManyToMany() {
		try {
			TableVO tableVO = new TableVO();
			tableVO.setClassName("Act_id_group");
			tableVO.setPackageName("process");
			tableVO.setTableName("act_id_group");
			tableVO.setRelationTableName("act_id_user");
			// 关联的另一边表设置
			tableVO.setRelationTableDataUrl("act_id_user/dataGrid");
			// 中间表的设置
			tableVO.setMiddleTableUrl("act_id_membership/dataGrid", "act_id_membership/insert", "act_id_membership/deleteAll");

			SubTableVO sub = new SubTableVO();
			sub.setClassName("Act_id_membership");
			sub.setPackageName("process");
			sub.setTableName("act_id_membership");

			sub.setRelationKeys("ID_", "GROUP_ID_");
			tableVO.addSubTable(sub);

			PoushengGenerator.generatorManyToMany(tableVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
