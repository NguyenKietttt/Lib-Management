/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Book;
import com.ndtk.res.BookRes;
import com.ndtk.service.BookService;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "bookBean")
@RequestScoped
public class BookBean {
    private static BookService bookSvc = new BookService();
    
    private static ArrayList<BookRes> listBook;
        
    private int id;
    private String bookName;
    private String status;
    private int categoryID = -1;
    private int authorID = -1;
    private int publisherID = -1;
    private String keyword;
    
    public BookBean(){
        ArrayList<Book> books = bookSvc.getAllBooks(this.keyword, this.categoryID, this.authorID, this.publisherID);
        ArrayList<BookRes> booksRes = initBookRes(books);
        setListBook(booksRes);
    }
    
    public ArrayList<BookRes> searchItem(){
        String temp = this.keyword.trim().replaceAll("\\s+", " ");
        
        if (temp.equals("")) {
            temp = null;
        }
        
        ArrayList<Book> books = bookSvc.getAllBooks(temp, this.categoryID, this.authorID, this.publisherID);
       
        ArrayList<BookRes> booksRes = initBookRes(books);
        
        setListBook(booksRes);

        return listBook;
    }
    
    private ArrayList<BookRes> initBookRes(ArrayList<Book> books){
        ArrayList<BookRes> booksRes = new ArrayList<>();
        String status;
        
        for (Book b : books) {
            if (b.getBookStatus())
                status = "Còn";
            else
                status = "Hết";
                
            BookRes bRes = new BookRes(b.getBookID(), b.getBookName(), b.getImage(), status);
            booksRes.add(bRes);
        }
        
        return booksRes;
    }

    // <editor-fold defaultstate="collapsed" desc=" Crawl Books ">
//    public void crawlBook() throws IOException, URISyntaxException{
//        String pathExcel = FacesContext.getCurrentInstance()
//        .getExternalContext().getInitParameter("com.ndtk.ExcelPath") + "\\" + "Book.xlsx";
//        try (InputStream inputStream = new FileInputStream(new File(pathExcel))) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//            Iterator<Row> itr = sheet.iterator();
//            outerloop:
//                while (itr.hasNext()){
//                    Row row = itr.next();
//                    Iterator<Cell> cellIterator = row.cellIterator();
//
//                    Book book = new Book();
//
//                    while (cellIterator.hasNext()){
//                        Cell cell = cellIterator.next();
//                        if (book.getBookName() == null) {
//                            Document doc = Jsoup.connect(cell.getStringCellValue()).get();
//                            Element elm = doc.select("body > div.container > div.row > div.col-md-9 > div").first();
//
//                            String name = elm.select("div > div.row.thong_tin_ebook > div.col-md-8 > a:nth-child(1) > h1").first().text();
//                            book.setBookName(name);
//
//                            String img = elm.select("div > div.row.thong_tin_ebook > div.col-md-4.cover > img").first().absUrl("src");
//                            URL url = new URL(img);
//                            URLConnection urlc = url.openConnection();
//                            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
//                                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
//                            String imgName = Paths.get(new URI(img).getPath()).getFileName().toString();
//                            String path = FacesContext.getCurrentInstance()
//                                    .getExternalContext().getInitParameter("com.ndtk.ImgPath") + "\\" + imgName;
//                            try(InputStream in = new BufferedInputStream(urlc.getInputStream())){
//                                OutputStream out = new BufferedOutputStream(new FileOutputStream(path));
//                                byte[] b = new byte[1024];
//                                int byteRead;
//                                while((byteRead = in.read(b)) != -1){
//                                    out.write(b, 0, byteRead);
//                                }
//
//                                in.close();
//                                out.close();
//                                book.setImage(imgName);
//                            };
//
//                            String fullDescription = "";
//                            Elements descriptions = elm.select("div > div.gioi_thieu_sach.text-justify > p");
//                            for (int i = 0; i < descriptions.size(); i++) {
//                                String e = descriptions.first().text();
//                                fullDescription = fullDescription + e + " ";
//                            }
//                            book.setBookDescription(fullDescription);
//
//                            String categoryName = elm.select("div > div.row.thong_tin_ebook > div.col-md-8 > h5:nth-child(4) > a").first().text();
//                            Category cate = cateSvc.getCategoryByName(categoryName);
//                            book.setCategory(cate);
//
//                            String authorName = elm.select("div > div.row.thong_tin_ebook > div.col-md-8 > h5:nth-child(3)")
//                                    .first().text().substring(10);
//                            Author author = authorSvc.getAuthorByName(authorName);
//                            if (author == null) {
//                                Author a = new Author();
//                                a.setAuthorName(authorName);
//                                authorSvc.addOrSaveAuthor(a);
//                                Author authorAfter = authorSvc.getAuthorByName(authorName);
//                                book.setAuthor(authorAfter);
//                            }
//                            else{
//                                book.setAuthor(author);
//                            }
//                        }
//                        else{
//                            if (book.getPublishingDate() == null) {
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                String date = dateFormat.format(cell.getDateCellValue());
//                                book.setPublishingDate(Date.valueOf(date));
//                            }
//                            else{
//                                if (cell.getStringCellValue().equals("")) {
//                                    break;
//                                }
//                                String publisherName = cell.getStringCellValue();
//                                Publisher publisher = publisherSvc.getPublisherByName(publisherName);
//                                if (publisher == null) {
//                                    Publisher a = new Publisher();
//                                    a.setPublisherName(publisherName);
//                                    publisherSvc.addOrSavePubliser(a);
//                                    Publisher publisherAfter = publisherSvc.getPublisherByName(publisherName);
//                                    book.setPublisher(publisherAfter);
//                                }
//                                else{
//                                    book.setPublisher(publisher);
//                                }
//
//                                bookSvc.addOrSaveBook(book);
//                            }
//                        }
//                    }
//                }
//            workbook.close();
//        }
//    }
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the bookSvc
     */
    public static BookService getBookSvc() {
        return bookSvc;
    }

    /**
     * @param aBookSvc the bookSvc to set
     */
    public static void setBookSvc(BookService aBookSvc) {
        bookSvc = aBookSvc;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * @param bookName the bookName to set
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the category
     */
    public int getCategory() {
        return categoryID;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the author
     */
    public int getAuthor() {
        return authorID;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(int authorId) {
        this.authorID = authorId;
    }

    /**
     * @return the publisher
     */
    public int getPublisher() {
        return publisherID;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(int publisherId) {
        this.publisherID = publisherId;
    }
    
    /**
     * @return the listBook
     */
    public ArrayList<BookRes> getListBook() {
        return listBook;
    }

    /**
     * @param listBook the listBook to set
     */
    public void setListBook(ArrayList<BookRes> listBook) {
        this.listBook = listBook;
    }
    
    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    // </editor-fold>
}
