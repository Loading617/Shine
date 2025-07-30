#ifndef _Shine_Shine_h
#define _Shine_Shine_h

#include <CtrlLib/CtrlLib.h>

using namespace Upp;

#define LAYOUTFILE <Shine/Shine.lay>
#include <CtrlCore/lay.h>

class Shine : public WithShineLayout<TopWindow> {
public:
	Shine();
};

#endif
