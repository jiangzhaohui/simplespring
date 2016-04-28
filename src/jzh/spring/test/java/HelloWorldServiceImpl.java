public class HelloWorldServiceImpl implements HelloWorldService {

	private String text;
	
    private OutputService outputService;
    
    public HelloWorldServiceImpl(){
    	System.err.println("new a helloworldservice object ...");
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setOutputService(OutputService outputService) {
        this.outputService = outputService;
    }


	@Override
	public void helloWorld() {
		outputService.output(text);
		
	}


	public String getText() {
		return text;
	}


	public OutputService getOutputService() {
		return outputService;
	}

	
}
