package contracts;

public interface Handler {
    public double handle(double sum, int productsSize);
}

//public class ClientTypeSpecialHandler implements Handler {
//    @Override
//    public double handle(double sum, int productsSize) {
//        if (productsSize > 10) {
//            return (sum - 100);
//        }
//
//        return new ClientTypeFidel().handle(sum, productsSize);
//    }
//}
//
//public class ClientTypeFidel implements Handler {
//    @Override
//    public double handle(double sum, int productsSize) {
//        if (productsSize > 8) {
//            return (sum - 200);
//        }
//
//        return new ClientTypeExclusive().handle(sum, productsSize);
//    }
//}
//
//public class ClientTypeExclusive implements Handler {
//    @Override
//    public double handle(double sum, int productsSize) {
//        if (productsSize > 5) {
//            return (sum - 300);
//        }
//
//        return sum;
//    }
//}