To generate DoddFrank classes from XSD:

1. Put all D&F XDS's into [xsd/doddfrank] folder
2. Open Ant window in Eclipse 
	2.1 Click on [Menu\Window\Show View\Other]
	2.2 Type [ant]
	2.3 Select [Ant] then press [Ok]
3. Drag&Drop [build.xml] into [Ant] window
4. Open [CalypsoXSD] node in [Ant] window
5. Double click on [jaxb.dodd-frank.generate] target for start generation process
6. All generated classes placed in [generate] folder into [calypsox.tk.util.apollo.generated.calypsoinmessage] package
7. Copy&Paste generated classes into Calypso project
