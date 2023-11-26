package services;

import com.clothingstore.bus.CustomerBUS;
import com.clothingstore.bus.ImportBUS;
import com.clothingstore.bus.ImportItemsBUS;
import com.clothingstore.bus.OrderItemBUS;
import com.clothingstore.bus.PaymentBUS;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.models.CustomerModel;
import com.clothingstore.models.ImportItemsModel;
import com.clothingstore.models.ImportModel;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.OrderModel;
import com.clothingstore.models.PaymentModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.UserModel;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;

public class PDFWriter {

  private static final String FONT_FILE_PATH = "./src/main/java/resources/fonts/Arial.ttf";
  private static PDFWriter instance;

  public Document document;
  private Font fontData;
  private Font fontTitle;
  private Font fontHeader;

  private PDFWriter() {
    try {
      initializeFonts();
    } catch (IOException | DocumentException e) {
      throw new RuntimeException("Failed to initialize fonts", e);
    }
  }

  public static synchronized PDFWriter getInstance() {
    if (instance == null) {
      instance = new PDFWriter();
    }
    return instance;
  }

  private void initializeFonts() throws IOException, DocumentException {
    BaseFont baseFont = BaseFont.createFont(
        FONT_FILE_PATH,
        BaseFont.IDENTITY_H,
        BaseFont.EMBEDDED);
    fontData = new Font(baseFont, 11, Font.NORMAL);
    fontTitle = new Font(baseFont, 25, Font.NORMAL);
    fontHeader = new Font(baseFont, 13, Font.NORMAL);
  }

  public void chooseURL(String filePath) {
    try {
      document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(filePath + ".pdf"));
      document.open();
    } catch (IOException | DocumentException ex) {
      Logger.getLogger(PDFWriter.class.getName()).log(Level.SEVERE, null, ex);
      throw new RuntimeException("Failed to open PDF file", ex);
    }
  }

  public void setTitle(String title) {
    Paragraph pdfTitle = new Paragraph(new Phrase(title, fontTitle));
    pdfTitle.setAlignment(Element.ALIGN_CENTER);
    try {
      document.add(pdfTitle);
      document.add(new Paragraph(Chunk.NEWLINE));
    } catch (DocumentException ex) {
      Logger.getLogger(PDFWriter.class.getName()).log(Level.SEVERE, null, ex);
      throw new RuntimeException("Failed to write title to PDF", ex);
    }
  }

  public void writeObject(String[] data) {
    for (String datum : data) {
      Paragraph pdfData = new Paragraph(datum, fontData);
      pdfData.setSpacingAfter(10f); // Add some spacing after each line
      try {
        document.add(pdfData);
      } catch (DocumentException ex) {
        Logger.getLogger(PDFWriter.class.getName()).log(Level.SEVERE, null, ex);
        throw new RuntimeException("Failed to write object to PDF", ex);
      }
    }
  }

  public void writeTable(JTable table) {
    PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
    pdfTable.setHeaderRows(1);
    for (int i = 0; i < table.getColumnCount(); i++) {
      String header = table.getColumnName(i);
      PdfPCell cell = new PdfPCell(new Phrase(header, fontHeader));
      pdfTable.addCell(cell);
    }
    for (int i = 0; i < table.getRowCount(); i++) {
      for (int j = 0; j < table.getColumnCount(); j++) {
        String data = String.valueOf(table.getValueAt(i, j));
        PdfPCell cell = new PdfPCell(new Phrase(data, fontData));
        pdfTable.addCell(cell);
      }
    }
    try {
      document.add(pdfTable);
    } catch (DocumentException ex) {
      Logger.getLogger(PDFWriter.class.getName()).log(Level.SEVERE, null, ex);
      throw new RuntimeException("Failed to write table to PDF", ex);
    }
  }

  public void close() {
    if (document != null) {
      document.close();
    }
  }

  public void exportImportsToPDF(int importId, String filepath) {
    ImportModel importData = ImportBUS.getInstance().getModelById(importId);
    List<ImportItemsModel> importItemsList = new ArrayList<>(
        ImportItemsBUS.getInstance().searchModel(importId + "", new String[] { "import_id" }));
    UserModel employee = UserBUS.getInstance().getModelById(importData.getUserId());

    double totalPrice = importItemsList.stream()
        .mapToDouble(item -> {
          return item.getQuantity() * item.getPrice();
        })
        .sum();

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    String formattedTotalPrice = currencyFormatter.format(totalPrice);

    chooseURL(filepath);
    try {
      setTitle("Hóa đơn nhập hàng");

      // Add header information
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String headerInfoString = String.format(
          "Mã phiếu nhập: %d\nHọ tên nhân viên: %s\nTổng tiền: %s\nNgày khởi tạo: %s",
          importData.getId(), employee.getName(), formattedTotalPrice, dateFormat.format(importData.getImportDate()));
      writeObject(headerInfoString.split("\n"));

      // Add product information
      Set<Integer> uniqueProductIds = new HashSet<>();

      for (ImportItemsModel importItemsModel : importItemsList) {
        uniqueProductIds.add(importItemsModel.getProduct_id());
      }

      int numberOfUniqueProductIds = uniqueProductIds.size();
      String[] columnNames = { "Mã sản phẩm", "Tên", "Giá tiền", "Kích cỡ và Số lượng", "Tổng từng loại" };
      Object[][] data = new Object[numberOfUniqueProductIds][5];
      int id = 0;
      int count = 0;
      
      for (int i = 0; i < importItemsList.size() && count < numberOfUniqueProductIds; i++) {
          if (id != importItemsList.get(i).getProduct_id()) {
              ImportItemsModel item = importItemsList.get(i);
              ProductModel product = ProductBUS.getInstance().getModelById(item.getProduct_id());
              double itemTotalPrice = item.getQuantity() * item.getPrice();
      
              List<ImportItemsModel> importItemListByImportId = new ArrayList<>();
              for (ImportItemsModel importItemsModel : importItemsList) {
                  if (importItemsModel.getProduct_id() == item.getProduct_id()) {
                      importItemListByImportId.add(importItemsModel);
                  }
              }
              StringBuilder sizeAndQuantityBuilder = new StringBuilder();
              StringBuilder priceBuilder = new StringBuilder();
      
              for (ImportItemsModel importItemsModel : importItemListByImportId) {
                  sizeAndQuantityBuilder.append(String.format("%s - %d\n", getSizeLabel(importItemsModel.getSize_id()),
                          importItemsModel.getQuantity()));
      
                  double totalPrices = importItemsModel.getPrice() * importItemsModel.getQuantity();
                  priceBuilder.append(String.format("%s\n", currencyFormatter.format(totalPrices)));
              }
      
              String sizeAndQuantity = sizeAndQuantityBuilder.toString();
              String totalPriceString = priceBuilder.toString();
      
              data[count][0] = product.getId();
              data[count][1] = product.getName();
              data[count][2] = currencyFormatter.format(item.getPrice());
              data[count][3] = sizeAndQuantity;
              data[count][4] = totalPriceString;
      
              id = importItemsList.get(i).getProduct_id();
              count++;
          }
      }
      

      JTable table = new JTable(data, columnNames);
      writeTable(table);
      close();
    } catch (Exception e) {
      logError(e);
    }
  }

  private String getSizeLabel(int sizeId) {
    switch (sizeId) {
      case 1:
        return "S";
      case 2:
        return "M";
      case 3:
        return "L";
      case 4:
        return "XL";
      case 5:
        return "XXL";
      default:
        return "Unknown";
    }
  }

  private void logError(Exception e) {
    // Implement error logging mechanism here
    e.printStackTrace();
    // Log the error using a logging framework or custom logging mechanism
  }

  public void exportReceiptToPDF(OrderModel orderModel, String filepath, double totalPrice) {
    OrderItemBUS.getInstance().refreshData();
    List<OrderItemModel> orderItemsList = new ArrayList<>(
        OrderItemBUS.getInstance().searchModel(String.valueOf(orderModel.getId()), new String[] { "order_id" }));

    CustomerModel customer = CustomerBUS.getInstance().getModelById(orderModel.getCustomerId());
    UserModel employee = UserBUS.getInstance().getModelById(orderModel.getUserId());

    List<PaymentModel> paymentData = PaymentBUS.getInstance().searchModel(String.valueOf(orderModel.getId()),
        new String[] { "order_id" });
    String paymentMethod = null;
    PaymentModel payment = null;

    if (paymentData.size() == 1) {
      payment = paymentData.get(0);
      if (payment.getPaymentMethodId() == 1) {
        paymentMethod = "Tiền mặt";
      } else {
        paymentMethod = "Thẻ tín dụng";
      }
    }

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    String formattedTotalPrice = currencyFormatter.format(totalPrice);

    chooseURL(filepath);
    try {
      setTitle("Hóa đơn mua hàng");

      // Add Order Information
      Timestamp orderDateTimestamp = orderModel.getOrderDate();
      LocalDate orderDate = orderDateTimestamp.toLocalDateTime().toLocalDate();
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String orderInfoString = String.format(
          "Ngày mua hàng: %s\nMã đơn hàng: %d\nHọ tên khách hàng: %s\nNhân viên bán hàng: %s\nPhương thức thanh toán: %s\nTổng giá trị thanh toán: %s",
          dateFormat.format(orderDate), orderModel.getId(), customer.getCustomerName(), employee.getName(),
          paymentMethod, formattedTotalPrice);
      writeObject(orderInfoString.split("\n"));

      // Add product Information
      String[] columnNames = { "Mã sản phẩm", "Tên sản phẩm", "Giá tiền", "Kích cỡ", "Số lượng", "Tổng tiền từng kích cỡ" };
      Object[][] data = new Object[orderItemsList.size()][6];
      for (int i = 0; i < orderItemsList.size(); i++) {
        OrderItemModel orderItemModel = orderItemsList.get(i);
        ProductModel product = ProductBUS.getInstance().getModelById(orderItemModel.getProductId());
        double itemTotalPrice = orderItemModel.getQuantity() * product.getPrice();
        data[i][0] = product.getId();
        data[i][1] = product.getName();
        data[i][2] = product.getPrice();
        data[i][3] = getSizeLabel(orderItemModel.getSizeId());
        data[i][4] = orderItemModel.getQuantity();
        data[i][5] = currencyFormatter.format(itemTotalPrice);
      }
      JTable table = new JTable(data, columnNames);
      writeTable(table);

      close();
    } catch (Exception e) {
      logError(e);
    }
  }

}