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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
    List<ImportItemsModel> importItemsList = new ArrayList<>(ImportItemsBUS.getInstance().getAllModels());
    List<ProductModel> productsList = ProductBUS.getInstance().getAllModels();
    importItemsList.removeIf(item -> productsList.stream().noneMatch(product -> item.getId() == product.getId()));

    UserModel employee = UserBUS.getInstance().getModelById(importData.getUserId());

    double totalPrice = importItemsList.stream()
        .mapToDouble(item -> {
          ProductModel productModel = ProductBUS.getInstance().getModelById(item.getId());
          return item.getQuantity() * productModel.getPrice();
        })
        .sum();

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    String formattedTotalPrice = currencyFormatter.format(totalPrice);

    chooseURL(filepath);
    try {
      setTitle("Import Receipt");

      // Add header information
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String headerInfoString = String.format("ID: %d\nEmployee: %s\nTotal Price: %s\nCreated At: %s",
          importData.getId(), employee.getName(), formattedTotalPrice, dateFormat.format(importData.getImportDate()));
      writeObject(headerInfoString.split("\n"));

      // Add product information
      String[] columnNames = { "ID", "Name", "Image", "Price", "Size", "Quantity", "Total Price" };
      Object[][] data = new Object[importItemsList.size()][7];
      for (int i = 0; i < importItemsList.size(); i++) {
        ImportItemsModel item = importItemsList.get(i);
        ProductModel product = ProductBUS.getInstance().getModelById(item.getId());
        double itemTotalPrice = item.getQuantity() * product.getPrice();
        data[i][0] = product.getId();
        data[i][1] = product.getName();
        // Image handling...
        data[i][3] = currencyFormatter.format(product.getPrice());
        data[i][4] = getSizeLabel(item.getSize_id());
        data[i][5] = item.getQuantity();
        data[i][6] = currencyFormatter.format(itemTotalPrice);
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

  public void exportReceiptToPDF(OrderModel orderModel, String filepath) {
    List<OrderItemModel> orderItemsList = new ArrayList<>(OrderItemBUS.getInstance().getAllModels());

    orderItemsList.removeIf(orderItem -> orderItem.getOrderId() != orderModel.getId());

    List<ProductModel> productList = orderItemsList.stream()
        .map(orderItem -> ProductBUS.getInstance().getModelById(orderItem.getProductId()))
        .collect(Collectors.toList());

    CustomerModel customer = CustomerBUS.getInstance().getModelById(orderModel.getCustomerId());
    UserModel employee = UserBUS.getInstance().getModelById(orderModel.getUserId());

    List<PaymentModel> paymentData = PaymentBUS.getInstance().searchModel(String.valueOf(orderModel.getId()),
        new String[] { "order_id" });
    String paymentMethod = null;
    PaymentModel payment = null;

    if (paymentData.size() == 1) {
      payment = paymentData.get(0);
      if (payment.getPaymentMethodId() == 1) {
        paymentMethod = "Cash";
      } else {
        paymentMethod = "Credit";
      }
    }

    double totalPrice = orderItemsList.stream()
        .mapToDouble(cartItem -> {
          ProductModel product = ProductBUS.getInstance().getModelById(cartItem.getProductId());
          return cartItem.getQuantity() * product.getPrice();
        })
        .sum();

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    String formattedTotalPrice = currencyFormatter.format(totalPrice);

    chooseURL(filepath);
    try {
      setTitle("Purchase Receipt");

      // Add Order Information
      Timestamp orderDateTimestamp = orderModel.getOrderDate();
      LocalDate orderDate = orderDateTimestamp.toLocalDateTime().toLocalDate();
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String orderInfoString = String.format(
          "Order Date: %s\nOrder ID: %d\nCustomer Name: %s\nEmployee Name: %s\nPayment Method: %s\nTotal Amount: %s",
          dateFormat.format(orderDate), orderModel.getId(), customer.getCustomerName(), employee.getName(),
          paymentMethod, formattedTotalPrice);
      writeObject(orderInfoString.split("\n"));

      // Add product Information
      String[] columnNames = { "ID", "Name", "Price", "Size", "Quantity", "Total Price" };
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