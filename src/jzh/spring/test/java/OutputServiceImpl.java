import org.junit.Assert;

public class OutputServiceImpl implements OutputService {
	private HelloWorldService helloWorldService;
	
	public OutputServiceImpl(){
		System.err.println("new a outputservice object ...");
	}

	@Override
    public void output(String text){
        Assert.assertNotNull(helloWorldService);
        System.out.println(text);
    }

	public HelloWorldService getHelloWorldService() {
		return helloWorldService;
	}

	public void setHelloWorldService(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

    
}
