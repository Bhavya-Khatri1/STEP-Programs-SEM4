import java.util.*;
public class FlashSaleSystem
{
    private HashMap<String, Integer> stock = new HashMap<>();
    private HashMap<String, Queue<Integer>> waitingList = new HashMap<>();
    public FlashSaleSystem()
    {
        stock.put("Iphone15_256GB",100);
        waitingList.put("Iphone15_256GB",new LinkedList<>());
    }
    public int checkStock(String productId)
    {
        return stock.getOrDefault(productId,0);
    }
    public synchronized String purchaseItem(String productId,int userId)
    {
        int currentStock=stock.getOrDefault(productId,0);
        if(currentStock>0)
        {
            stock.put(productId,currentStock-1);
            return "Success, "+(currentStock-1)+" units remaining"; 
        }
        else
        {
            waitingList.get(productId).add(userId);
            int position=waitingList.get(productId).size();
            return "Added to waiting list, position #"+position;
        }
    }
}