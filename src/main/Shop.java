package main;

import model.Product;
import model.Sale;
import model.Amount;
import java.util.Scanner;
import java.util.ArrayList;

public class Shop {

    private Amount cash = new Amount(100.0);
    private Product[] inventory;
    private int numberProducts;
    private Sale[] sales;
    private int salesIndex = 0;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new Product[10];
        sales = new Sale[10];
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Mostrar total de las ventas");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;

                case 8:
                    shop.showTotalSales();
                    break;
            }
        } while (opcion != 10);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        addProduct(new Product("Manzana", new Amount(10.00), new Amount(12.5), true, 10));
        addProduct(new Product("Pera", new Amount(20.00), new Amount(25.00), true, 20));
        addProduct(new Product("Hamburguesa", new Amount(30.00), new Amount(38.75), true, 30));
        addProduct(new Product("Fresa", new Amount(5.00), new Amount(6.25), true, 20));
    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: " + cash); 
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                System.out.println("El producto " + name + " ya est� en el inventario");
                return;
            }
        }
        
        System.out.print("Precio publico: ");
        Amount publicPrice = new Amount(scanner.nextDouble());
        System.out.print("Precio mayorista: ");
        Amount wholesalerPrice = new Amount(scanner.nextDouble());
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        addProduct(new Product(name, wholesalerPrice, publicPrice, true, stock));
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            // update stock product
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());

        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product.getName());
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        // ask for client name
        Scanner sc = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente");
        String client = sc.nextLine();

        // sale product until input name is not 0
        ArrayList<Product> order = new ArrayList<>();
        
        Amount totalAmount = new Amount();
        String name = "";
        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);
            boolean productAvailable = false;

            if (product != null && product.isAvailable()) {
                productAvailable = true;
                totalAmount.addValue(product.getPublicPrice().getValue());
                product.setStock(product.getStock() - 1);
                // if no more stock, set as not available to sale
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                order.add(new Product(name, product.getWholesalerPrice(), product.getPublicPrice(), product.isAvailable(), product.getStock()));
                System.out.println("Producto añadido con éxito");
            }

            if (!productAvailable) {
                System.out.println("Producto no encontrado o sin stock");
            }
        }

        totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
        cash.addValue(totalAmount.getValue()) ;
        
        Product[] orderArray = order.toArray(new Product[0]); 
        sales[salesIndex] = new Sale(client, orderArray, totalAmount);
        salesIndex++;
        
        // show cost total
        System.out.println("Venta realizada con éxito, total: " + totalAmount);
    }

    /**
     * show all sales
     */
    public void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println(sale.getClient() + ":");
                
                for (Product product : sale.getProducts()) {
                    System.out.println(product.getName());
                }
                
                System.out.println("");
            }
        }
    }

    /**
     * total amount of all sales 
     */
    public void showTotalSales() {
        Amount totalAmount = new Amount();
        
        for (Sale sale : sales) {
            if (sale != null) {
                totalAmount.setValue(sale.getAmount().getValue());
            }
        }
        
        System.out.println("Total de las ventas: " + totalAmount);
    }
    
   
    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.length);
            return;
        }
        inventory[numberProducts] = product;
        inventory[numberProducts].setPublicPrice(product.getWholesalerPrice());
        numberProducts++;
    }

    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */
    public boolean isInventoryFull() {
        if (numberProducts == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getName().equalsIgnoreCase(name)) {
                return inventory[i];
            }
        }
        return null;
    }
}
