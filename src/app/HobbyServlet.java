package app;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/hobby")
public class HobbyServlet extends HttpServlet {

	/********************************************************************************
	 * 以下のdoGet/doPostを実装して下さい。
	 * importなどは自由に行って下さい。
	 ********************************************************************************/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO 必須機能「趣味参照機能」
		String syainId=request.getParameter("syainId");
		try {
			// JDBCドライバのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
			// ドライバが設定されていない場合はエラーになります
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
			}

		String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
		String user = "wt2";
		String pass = "wt2";

		String sql="select \n" +
				"ROWNUM NO, \n" +
				"MC.CATEGORY_NAME, \n" +
				"MH.HOBBY_NAME, \n" +
				"MS.SYAINNAME \n" +
				"from \n" +
				"MS_SYAIN MS, \n" +
				"MS_SYAIN_HOBBY MSH, \n" +
				"MS_HOBBY MH, \n" +
				"MS_CATEGORY MC \n" +
				"where 1=1 \n" +
				"and MS.SYAINID='"+syainId+"' \n" +
				"and MS.SYAINID=MSH.SYAINID \n" +
				"and MSH.HOBBY_ID=MH.HOBBY_ID \n" +
				"and MH.CATEGORY_ID=MC.CATEGORY_ID \n" ;
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);

				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();

				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {

			// 社員情報を保持するため、Employee型の変数empを宣言
			// 変数empはJSPに渡すための社員情報を保持させます

			List<Hobby> hobbyList = new ArrayList<Hobby>();

			// SQL実行結果を保持している変数rsから社員情報を取得
			// rs.nextは取得した社員情報表に次の行があるとき、trueになります
			// 次の行がないときはfalseになります

			while (rs1.next()) {
				Hobby hobby = new Hobby();

				hobby.setNo(rs1.getString("NO"));
				hobby.setHobbyCategory(rs1.getString("CATEGORY_NAME"));
				hobby.setHobby(rs1.getString("HOBBY_NAME"));
				hobby.setsyainName(rs1.getString("SYAINNAME"));

				hobbyList.add(hobby);
			}

			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString(hobbyList));

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}



		// -- ここまで --
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO 任意機能「趣味投稿機能に挑戦する場合はこちらを利用して下さい」

		// -- ここまで --
	}

}
