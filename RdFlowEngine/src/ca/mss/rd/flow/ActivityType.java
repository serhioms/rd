package ca.mss.rd.flow;

public enum ActivityType {
	None, /* i.e. NoImplementation */
	Tool, /* i.e.  Application/Task = {TaskService,TaskReceive,TaskManual,TaskReference,TaskScript,TaskSend,TaskUser,TaskApplication i.e. Tool} */ 
	Task, /* i.e.  1 tool only - perform in same thread as activity-owner */ 
	SubFlow, /* = {ActualParameters,ASYNCHR/SYNCHR}, StartActivityId/StartActivitySetId,DataMappings={Actual,Formal,direction={IN/OUT/INOUT}}}  */
	Route, /* i.e. GateWay, Dummy */
	Event, /* = {StartEvent={***}, IntermediateEvent={***}, EndEvent={***}} */
	ActivitySet, /* i.e. BlockActivity = {ActivitySetId, StartActivityId}, Component - named activity set */
	Reference /* = {ActivityId} */
	; 
}
