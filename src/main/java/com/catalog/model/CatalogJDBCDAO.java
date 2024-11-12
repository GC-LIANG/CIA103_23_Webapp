package com.catalog.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;
public class CatalogJDBCDAO implements CatalogDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/fakeDataTest?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "eric19991003";

	private static final String INSERT_STMT = 
		"INSERT INTO prd_catalog (name) VALUES (?)";
	private static final String GET_ALL_STMT = 
		"SELECT id,name FROM prd_catalog order by id";
	private static final String GET_ONE_STMT = 
		"SELECT id,name FROM prd_catalog where id = ?";
	private static final String DELETE = 
		"DELETE FROM prd_catalog where id = ?";
	private static final String UPDATE = 
		"UPDATE prd_catalog set name=? where id = ?";
	
	public void insert(CatalogVO catalogVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS);
			
//			pstmt.setInt(1, catalogVO.getId());
			pstmt.setString(1, catalogVO.getName());
			
			pstmt.executeUpdate();
			
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                catalogVO.setId(generatedKeys.getInt(1));  // 设置生成的 id
	            }
	        }
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Handle any driver errors
	    } catch (ClassNotFoundException e) {
		    throw new RuntimeException("Couldn't load database driver. "
				+ e.getMessage());
	    } finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	// 改
	public void update(CatalogVO catalogVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, catalogVO.getName());
			pstmt.setInt(2, catalogVO.getId());
			
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
	// 刪
	public void delete(Integer id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	// 找單筆
	public CatalogVO findByPrimaryKey(Integer id) {
		
		CatalogVO catalogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				catalogVO = new CatalogVO();
				catalogVO.setId(rs.getInt("id"));
				catalogVO.setName(rs.getString("name"));
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return catalogVO;
	}
	
	// 找多筆
	public List<CatalogVO> getAll(){
		List<CatalogVO> list = new ArrayList<CatalogVO>();
		CatalogVO catalogVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				catalogVO = new CatalogVO();
				catalogVO.setId(rs.getInt("id"));
				catalogVO.setName(rs.getString("name"));
				list.add(catalogVO);
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	// main
	public static void main(String[] args) {
		CatalogJDBCDAO dao = new CatalogJDBCDAO();
		
	}


}
