package rc;

public class TestAC {

	class ASTATE {
		public int l, h;

		public int pm[]; // mid

		public int po[][]; // o/p
		public int pc[][]; // count
		public int ps[][]; // next
	}

	// state [npart 0o 0c 0s 1o 1c 1s]

	public int asi;
	public ASTATE as[];

	public void init() {
		asi = 1;
		as = new ASTATE[256];
		for (int i = 0; i < as.length; i++) {
			as[i] = new ASTATE();
			as[i].pm = new int[8];

			as[i].po = new int[8][2];
			as[i].pc = new int[8][2];
			as[i].ps = new int[8][2];
			as[i].l = 0;
			as[i].h = 7;
		}
		// as[0].l=0;as[0].h=7;
	}

	public void dump() {
		for (int i = 0; i < asi; i++) {
			System.out.println("===>(s)" + i + " (l)" + as[i].l + ",(h)"
					+ as[i].h);
			for (int j = 0; j < as[i].pm.length; j++) {
				System.out.println("(p)" + j + " (m)" + as[i].pm[j] + " (1)"
						+ as[i].pc[j][1] + " (n1)" + as[i].ps[j][1] + " (0)"
						+ as[i].pc[j][0] + " (n0)" + as[i].ps[j][0]);
			}
		}
	}

	public int sfind(int nl, int nh) {
		for (int i = 0; i < asi; i++) {
			if (as[i].l == nl && as[i].h == nh) {
				return i;
			}
		}
		int i = asi;
		asi += 1;
		return i;
	}

	public void run() {
		init();
		for (int i = 0; i < asi; i++) {
			for (int j = 0; j < as[i].pm.length; j++) {
				as[i].pm[j] = as[i].l + (((as[i].h - as[i].l) * j) >> 3);
				for (int b = 0; b < 2; b++) {
					int nl = 0, nh = 0;
					if (b == 1) {// 1
						nl = as[i].l;
						nh = as[i].pm[j];
					} else { // 0
						nl = as[i].pm[j] + 1;
						nh = as[i].h;
					}
					as[i].pc[j][b] = 0;
					while (((nl ^ nh) & 0x4) == 0) {
						nl = ((nl << 1) & 7);
						nh = ((nh << 1) & 7) + 1;
						as[i].pc[j][b]++;
					}
					// find next
					int ni = sfind(nl, nh);
					as[ni].l = nl;
					as[ni].h = nh;
					as[i].ps[j][b] = ni;
				}
			}
		}
		dump();
	}

	class PSTATE {
		public int x;
		public int y;
		public int n1;
		public int n0;
		public int p;
	}

	public PSTATE ps[];
	public int psi;

	public void rinit() {
		psi = 0;
		ps = new PSTATE[256];
		for (int i = 0; i < ps.length; i++)
			ps[i] = new PSTATE();
	}

	public int recprob(int x, int y) {
		int s = -1;
		for (int i = 0; i < psi; i++) {
			if (ps[i].x == x && ps[i].y == y) {
				s = i;
				break;
			}
		}
		// int s=0;
		if (s == -1) {
			s = psi;
			psi += 1;
			ps[s].x = x;
			ps[s].y = y;
			int max = 32; // 100
			ps[s].n1 = recprob((x < max ? x + 1 : x), (y+3)/4 );
			ps[s].n0 = recprob( ((x+3)/4) , (y < max ? y + 1 : y));
			ps[s].p = ((x * 4 + 1) << 3) / (x * 4 + y * 4 + 2); // <<8
		}
		return s;
	}

	public void rdump() {
		for (int i = 0; i < psi; i++) {
			System.out.println("(s)" + i + " x:" + ps[i].x + " y:" + ps[i].y
					+ " 1:" + ps[i].n1 + " 0:" + ps[i].n0 + " p:" + ps[i].p);
		}
	}
	
	public void rdumpJava() {
		int linebreak=5;
		for (int i = 0; i < psi; i++) {
			System.out.print(" {" + ps[i].n0 + " ," + ps[i].n1 + " ," + ps[i].p+"},");
			linebreak--;
			if(linebreak==0) {System.out.println();linebreak=5;}
		}
	}

	public void rprob() {
		rinit();
		recprob(1, 1);
		rdump();
		rdumpJava();
	}

	public static void main(String[] args) {
		TestAC testAC = new TestAC();
		//testAC.run();
		testAC.rprob();
	}
}
