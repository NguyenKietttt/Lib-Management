/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.res.DashBoardRes;
import com.ndtk.service.BorrowReturnService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "dashBoardBean")
@RequestScoped
public class DashBoardBean {
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private static BorrowReturnService brSvc = new BorrowReturnService();
    
    private static List<Integer> listYear;
    private static Integer[] listQuarter = new Integer[]{1,2,3,4};
    private static List<BorrowReturn> listBorrow = new ArrayList<>();
    private static Map<Integer, Integer[]> mapQuarter = new HashMap<>();
    static {
        setMapQuarter(new HashMap<>());
        getMapQuarter().put(1, new Integer[]{1,2,3});
        getMapQuarter().put(2, new Integer[]{4,5,6});
        getMapQuarter().put(3, new Integer[]{7,8,9});
        getMapQuarter().put(4, new Integer[]{10,11,12});
    }
    
    private int year;
    private int quarter;
    private DashBoardRes dbAll = new DashBoardRes();
    private List<DashBoardRes> dbEachMonth = new ArrayList<>();
    private int[][] chart = new int[3][4];
    
    public DashBoardBean(){
        if (!FacesContext.getCurrentInstance().isPostback()) {
            listYear = new ArrayList<>();

            listYear = brSvc.getListYearBorrowReturn();
            this.year = listYear.get(0);
            
            this.quarter = 1;
        
            initDashBoard(this.year, 1);
            initChart();
            
            listBorrow = brSvc.getListBorrowByMonthAndYear(this.year, mapQuarter.get(1));
        }
    }
    
        
    public void initReport(int year, int quarter){
        initDashBoard(year, quarter);
        initChart();
    }
    
    private void initDashBoard(int year, int quarter){
        listBorrow = brSvc.getListBorrowByMonthAndYear(year, mapQuarter.get(quarter));
        
        List<BorrowReturnDetail> borrowBooks = brSvc.getBookBorrowByMonthAndYear(year, mapQuarter.get(quarter));
        
        // ALL
        dbAll.setBookborrow(borrowBooks.size());

        dbAll.setBookReturn(borrowBooks.stream().filter(
                p -> p.getBorrowReturn().getReturnDate() != null).collect(Collectors.toList()).size());

        dbAll.setBookLost(borrowBooks.stream().filter(
                p -> p.isLost() == true).collect(Collectors.toList()).size());

        List<Object[]> listFines = brSvc.getFinesByMonthAndYear(year, mapQuarter.get(quarter));
        BigDecimal sum = new BigDecimal(0);
        for (Object[] obj : listFines) {
            if ((BigDecimal) obj[1] != null) {
                sum = sum.add((BigDecimal) obj[1]);
            }
        }

        DecimalFormat df = new DecimalFormat("0.##"); 
        dbAll.setFines(df.format(sum) + " " + this.bundle.getString("bookreturndetail.money"));
        
        // Each Month
        for (int i = 0; i < 3; i++) {
            DashBoardRes db = new DashBoardRes();
            
            int count = i;
            db.setMonth(mapQuarter.get(quarter)[count]);
            
            List<BorrowReturnDetail> lisBRD = borrowBooks.stream().filter(
                p -> p.getBorrowReturn().getBorrowDate().toLocalDate().getMonthValue() == mapQuarter.get(quarter)[count])
                    .collect(Collectors.toList());
            
            db.setBookborrow(lisBRD.size());
            
            db.setBookReturn(lisBRD.stream().filter(
                p -> p.getBorrowReturn().getReturnDate() != null).collect(Collectors.toList()).size());
            
            db.setBookLost(lisBRD.stream().filter(
                p -> p.isLost() == true).collect(Collectors.toList()).size());
            
            BigDecimal sumMonth = new BigDecimal(0);
            for (Object[] obj : listFines) {
                java.sql.Date sqlDate = (java.sql.Date) obj[0];
                if (sqlDate.toLocalDate().getMonthValue() == mapQuarter.get(quarter)[count] && (BigDecimal) obj[1] != null) {
                    sumMonth = sumMonth.add((BigDecimal) obj[1]);
                }
            }
            
            db.setFines(df.format(sumMonth) + " " + this.bundle.getString("bookreturndetail.money"));
            
            getDbEachMonth().add(db);
        }
    }
    
    private void initChart(){
        int i = 0;
            
        for (DashBoardRes dbr : dbEachMonth) {
            for (int j = 0; j < 4; j++) {
                if (j == 0)
                    chart[i][j] = dbr.getMonth();
                else if (j == 1)
                    chart[i][j] = dbr.getBookborrow();
                else if (j == 2)
                    chart[i][j] = dbr.getBookReturn();
                else
                    chart[i][j] = dbr.getBookLost();
            }
            
            i++;
        }
    }

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the dbEachMonth
     */
    public List<DashBoardRes> getDbEachMonth() {
        return dbEachMonth;
    }

    /**
     * @param dbEachMonth the dbEachMonth to set
     */
    public void setDbEachMonth(List<DashBoardRes> dbEachMonth) {
        this.dbEachMonth = dbEachMonth;
    }

    /**
     * @return the chart
     */
    public int[][] getChart() {
        return chart;
    }

    /**
     * @param chart the chart to set
     */
    public void setChart(int[][] chart) {
        this.chart = chart;
    }

    /**
     * @return the listBorrow
     */
    public List<BorrowReturn> getListBorrow() {
        return listBorrow;
    }

    /**
     * @param listBorrow the listBorrow to set
     */
    public void setListBorrow(List<BorrowReturn> listBorrow) {
        this.listBorrow = listBorrow;
    }

    /**
     * @return the bundle
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * @param bundle the bundle to set
     */
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
    /**
     * @return the brSvc
     */
    public static BorrowReturnService getBrSvc() {
        return brSvc;
    }

    /**
     * @param aBrSvc the brSvc to set
     */
    public static void setBrSvc(BorrowReturnService aBrSvc) {
        brSvc = aBrSvc;
    }

    /**
     * @return the listQuarter
     */
    public Integer[] getListQuarter() {
        return listQuarter;
    }

    /**
     * @param listQuarter the listQuarter to set
     */
    public void setListQuarter(Integer[] listQuarter) {
        this.listQuarter = listQuarter;
    }
    
    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the quarter
     */
    public int getQuarter() {
        return quarter;
    }

    /**
     * @param quarter the quarter to set
     */
    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    /**
     * @return the mapQuarter
     */
    public static Map<Integer, Integer[]> getMapQuarter() {
        return mapQuarter;
    }

    /**
     * @param aMapQuarter the mapQuarter to set
     */
    public static void setMapQuarter(Map<Integer, Integer[]> aMapQuarter) {
        mapQuarter = aMapQuarter;
    }

    /**
     * @return the dbAll
     */
    public DashBoardRes getDbAll() {
        return dbAll;
    }

    /**
     * @param dbAll the dbAll to set
     */
    public void setDbAll(DashBoardRes dbAll) {
        this.dbAll = dbAll;
    }
    
    /**
     * @return the listYear
     */
    public List<Integer> getListYear() {
        return listYear;
    }

    /**
     * @param listYear the listYear to set
     */
    public void setListYear(List<Integer> listYear) {
        this.listYear = listYear;
    }
    // </editor-fold>
}
