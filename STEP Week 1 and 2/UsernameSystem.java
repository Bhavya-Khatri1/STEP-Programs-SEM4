import java.util.*;
public class UsernameSystem
{
    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> attempts = new HashMap<>();
    public UsernameSystem()
    {
        users.put("john_doe",101);
        users.put("admin",1);
        users.put("player123",202);
    }
    public boolean checkAvailability(String username)
    {
        attempts.put(username, attempts.getOrDefault(username,0)+1);
        return !users.containsKey(username);
    }
    public List<String> suggestAlternatives(String username)
    {
        List<String> suggestions = new ArrayList<>();
        int i=1;
        while(suggestions.size()<3)
        {
            String newName = username+i;
            if(!users.containsKey(newName))
            {
                suggestions.add(newName);
            }
            i++;
        }
        String alt=username.replace("_",".");
        if(!users.containsKey(alt))
        {
            suggestions.add(alt);
        }
        return suggestions;
    }
    public String getMostAttempted()
    {
        String maxUser="";
        int maxCount=0;
        for(String user : attempts.keySet())
        {
            int count=attempts.get(user);
            if(count>maxCount)
            {
                maxCount=count;
                maxUser=user;
            }
        }
        return maxUser;
    }
    public static void main(String args[])
    {
        UsernameSystem system = new UsernameSystem();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username=sc.nextLine();
        System.out.println(system.checkAvailability(username));
        if(!system.checkAvailability(username))
        {
            System.out.println(system.suggestAlternatives(username));
        }
        System.out.println("Most Attempted: "+system.getMostAttempted());
    }
}